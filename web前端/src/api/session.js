const key = 'identity'


export function getToken() {
    return window.sessionStorage.getItem(key);
}

export function setUser(user) {
    window.sessionStorage.setItem('user', JSON.stringify(user))
}

export function getUser() {
    return JSON.parse(window.sessionStorage.getItem('user'));
}

export function setToken(token) {
    window.sessionStorage.setItem(key, token);
}

export function removeToken() {
    window.sessionStorage.removeItem(key);
}