import Vue from 'vue';
import store from "@/js/store";
import Router from 'vue-router';
import HomePage from "@/pages/HomePage.vue";
import LoginPage from "@/pages/LoginPage.vue";
import RegisterPage from "@/pages/RegisterPage.vue";
import BookAppointmentPage from "@/pages/BookAppointmentPage.vue";
import {refreshTokens} from "@/js/user-auth";
import ProfilePage from "@/pages/ProfilePage.vue";
import ChatPage from "@/pages/ChatPage.vue";

Vue.use(Router);

const routes = [
    { path: '/', component: HomePage },
    { path: '/login', component: LoginPage },
    { path: '/register', component: RegisterPage },
    { path: '/book-appointment', component: BookAppointmentPage },
    { path: '/profile', component: ProfilePage },
    { path: '/chat/:id', component: ChatPage, props: true}
]

const router = new Router({
    mode: 'history',
    routes,
});

router.beforeEach(async (to, from, next) => {
    const publicPages = ['/login', '/register'];
    const authRequired = !publicPages.includes(to.path);

    const storage = JSON.parse(localStorage.getItem('user'));

    if (!authRequired && storage) {
        return next('/');
    }

    if (authRequired && !storage) {
        return next('/login');
    }

    if (store.getters.user === undefined && store.getters.loggedIn) {
        return await store.dispatch('refreshUser', storage.email, storage.accessToken)
            .then(() => next())
            .catch(async () => {
                await refreshTokens(storage.email, storage.refreshToken)
                    .then(response => {
                        if (response.accessToken) {
                            localStorage.setItem('user', JSON.stringify({email: storage.email, ...response}));
                            store.dispatch('refreshUser', storage.email, storage.accessToken)
                            return next();
                        }
                    })
                    .catch(() => {
                        store.commit("logout");
                        return next('/login');
                    });
            });
    }

    return next();
})

export default router;