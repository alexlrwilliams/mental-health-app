import Vuex from 'vuex'
import {getUser, login, logout, register} from "@/js/user-auth";
import router from "@/js/router";
import Vue from "vue";
import {User} from "@/js/user";

Vue.use(Vuex);

const user = JSON.parse(localStorage.getItem('user'));

const state = {
    status: {
        loggedIn: !!user
    },
    user: undefined
};

const mutations = {
    login(state, user) {
        state.status = { loggedIn: true };
        state.user = User.from(user);
        state.user.tokens = user.tokens;
        localStorage.setItem('user', JSON.stringify({...user.tokens, email: user.username}));
    },
    logout(state) {
        state.status = { loggedIn: false };
        state.user = null;
        localStorage.removeItem('user');
    },
    updateUser(state, user) {
        state.user = User.from(user);
        state.user.tokens = JSON.parse(localStorage.getItem('user'));
    }
};

const actions = {
    async login({ commit }, form) {
        return await login(form)
            .then(
                async tokens => {
                    let user = await getUser(form.email, tokens.accessToken);
                    await commit('login', {
                        ...user,
                        tokens
                    });
                    await router.push('/');
                }
            )
    },
    async logout({ commit, getters }) {
      await logout(getters.user.email, getters.user.accessToken)
          .then(async () => {
              commit("logout");
              await router.push('/login');
          });
    },
    async register({ commit }, form) {
        return await register(form)
            .then(
                async tokens => {
                    let user = await getUser(form.email, tokens.accessToken);
                    await commit('login', {
                        ...user,
                        tokens
                    });
                    await router.push('/');
                }
            );
    },
    async refreshUser({ commit }, email) {
        await getUser(email)
            .then(async user => await commit('updateUser', user))
    }
};

const getters = {
    user(state) {
        return state.user;
    },
    loggedIn(state) {
        return state.status.loggedIn;
    }
}
const store = new Vuex.Store({
    state,
    actions,
    mutations,
    getters
})

export default store;