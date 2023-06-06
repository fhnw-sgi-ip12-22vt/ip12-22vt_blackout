const btnEnglish = document.getElementById('btnEnglish');
const btnGerman = document.getElementById('btnGerman');
const btnFrench = document.getElementById('btnFrench');
const btnItalian = document.getElementById('btnItalian');

function getQueryParameterValue(key) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get(key);
}

btnEnglish.onclick = () => {
    localStorage.languageCode = 'en';
    redirect();
}
btnGerman.onclick = () => {
    localStorage.languageCode = 'de';
    redirect();
}
btnFrench.onclick = () => {
    localStorage.languageCode = 'fr';
    redirect();
}
btnItalian.onclick = () => {
    localStorage.languageCode = 'it';
    redirect();
}

function redirect() {
    if (getQueryParameterValue('roomId')) {
        window.location.href = '/get-random-question?roomId=' + getQueryParameterValue('roomId');
    } else if (getQueryParameterValue('exponatId')) {
        window.location.href = '/get-random-question?exponatId=' + getQueryParameterValue('exponatId');
    } else {
        window.location.href = '/error.html';
    }
}