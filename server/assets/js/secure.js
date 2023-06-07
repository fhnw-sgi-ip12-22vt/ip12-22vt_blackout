function getPasswordFromLocalStorage() {
    if (typeof Storage !== 'undefined') {
        if (localStorage.password) {
            return localStorage.password;
        }
    }
    return null;
}

function addPasswordToURL(password) {
    if (password) {
        let url = location.href;

        const passwordParamRegex = /[?&]password=[^&]+/g;
        url = url.replace(passwordParamRegex, '');

        const encodedPassword = encodeURIComponent(password);
        const passwordParam = url.includes('?') ? '&password=' + encodedPassword : '?password=' + encodedPassword;

        location.href = url + passwordParam;
    }
}

function handleLocationChange(route) {
    history.pushState({}, '', route);
    var password = getPasswordFromLocalStorage();
    addPasswordToURL(password);
    if (!password) location.reload();
}