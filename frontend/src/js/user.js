export class User {
    constructor(id = "", accessToken = "", refreshToken = "",
                email = "", role = "", firstName = "", lastName = "", address = "") {
        this._id = id;
        this._accessToken = accessToken;
        this._refreshToken = refreshToken;
        this._email = email;
        this._role = role;
        this._firstName = firstName;
        this._lastName = lastName;
        this._address = address;
    }

    static from(jsonUser) {
        let user = new User();
        user.id = jsonUser.id;
        user.firstName = jsonUser.firstName;
        user.lastName = jsonUser.lastName;
        user.address = jsonUser.address;
        user.role = jsonUser.role;
        user.email = jsonUser.username;
        return user;
    }

    get id() {
        return this._id;
    }

    set id(value) {
        this._id = value;
    }

    set tokens(tokens) {
        this._accessToken = tokens.accessToken;
        this._refreshToken = tokens.refreshToken;
    }

    get accessToken() {
        return this._accessToken;
    }

    get refreshToken() {
        return this._refreshToken;
    }

    get email() {
        return this._email;
    }

    set email(value) {
        this._email = value;
    }

    get role() {
        return this._role;
    }

    set role(value) {
        this._role = value;
    }

    get firstName() {
        return this._firstName;
    }

    set firstName(value) {
        this._firstName = value;
    }

    get lastName() {
        return this._lastName;
    }

    set lastName(value) {
        this._lastName = value;
    }

    get address() {
        return this._address;
    }

    set address(value) {
        this._address = value;
    }
}
