if (document.getElementById('btnLogin')) {
    document.getElementById('btnLogin').addEventListener('click', () => {
        let password = prompt('Gib das Passwort ein');
        if (!password) return;
        localStorage.password = password;
        handleLocationChange('/list-questions');
    })
}

if (document.getElementById('btnLogout')) {
    document.getElementById('btnLogout').addEventListener('click', () => {
        localStorage.clear();
        history.pushState({}, '', '/');
        window.location.reload();
    })
}

if (document.getElementById('linkCreateQuestion')) {
    document.getElementById('linkCreateQuestion').addEventListener('click', () => {
        handleLocationChange('/create-question');
    })
}

if (document.getElementById('linkListQuestions')) {
    document.getElementById('linkListQuestions').addEventListener('click', () => {
        handleLocationChange('/list-questions');
    })
}

if (document.getElementById('btnCreateQuestion')) {
    document.getElementById('btnCreateQuestion').addEventListener('click', () => {
        handleLocationChange('/create-question');
    })
}

if (document.getElementById('linkCreateExponat')) {
    document.getElementById('linkCreateExponat').addEventListener('click', () => {
        handleLocationChange('/create-exponat');
    })
}

if (document.getElementById('linkListExponats')) {
    document.getElementById('linkListExponats').addEventListener('click', () => {
        handleLocationChange('/list-exponats');
    })
}

if (document.getElementById('btnCreateExponat')) {
    document.getElementById('btnCreateExponat').addEventListener('click', () => {
        handleLocationChange('/create-exponat');
    })
}

if (document.getElementById('linkCreateRoom')) {
    document.getElementById('linkCreateRoom').addEventListener('click', () => {
        handleLocationChange('/create-room');
    })
}

if (document.getElementById('linkListRooms')) {
    document.getElementById('linkListRooms').addEventListener('click', () => {
        handleLocationChange('/list-rooms');
    })
}

if (document.getElementById('btnCreateRoom')) {
    document.getElementById('btnCreateRoom').addEventListener('click', () => {
        handleLocationChange('/create-room');
    })
}