if (!localStorage.languageCode) {
    localStorage.languageCode = 'de';
}

const questionString = document.getElementById('questionString');
const hintString = document.getElementById('hintString');
const answerString1 = document.getElementById('answerString1');
const answerString2 = document.getElementById('answerString2');
const answerString3 = document.getElementById('answerString3');
const explanationString = document.getElementById('explanationString');
const exponatTitle = document.getElementById('exponatTitle');
const exponatDescription = document.getElementById('exponatDescription');
const roomTitle = document.getElementById('roomTitle');
const roomDescription = document.getElementById('roomDescription');


function getQueryParameterValue(key) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get(key);
}

switch (localStorage.languageCode) {
    case 'en': {
        hintString.innerHTML = 'Click on all answers that you consider to be correct';
    }
    case 'de': {
        hintString.innerHTML = 'Klicke auf alle Antworten, die aus deiner Sicht richtig sind.';
    }
    case 'fr': {
        hintString.innerHTML = 'Clique sur toutes les réponses que tu considères comme étant correctes.';
    }
    case 'it': {
        hintString.innerHTML = 'Clicca su tutte le risposte che ritieni corrette.';
    }
    default: {
        hintString.innerHTML = 'Klicke auf alle Antworten, die aus deiner Sicht richtig sind.';
    }
}

questionString.innerHTML = getQueryParameterValue('questionString' + localStorage.languageCode.toUpperCase());
answerString1.innerHTML = getQueryParameterValue('answerString1' + localStorage.languageCode.toUpperCase());
answerString2.innerHTML = getQueryParameterValue('answerString2' + localStorage.languageCode.toUpperCase());
answerString3.innerHTML = getQueryParameterValue('answerString3' + localStorage.languageCode.toUpperCase());
explanationString.innerHTML = getQueryParameterValue('explanationString' + localStorage.languageCode.toUpperCase());
exponatTitle.innerHTML = getQueryParameterValue('exponatTitle');
exponatDescription.innerHTML = getQueryParameterValue('exponatDescription');
roomTitle.innerHTML = getQueryParameterValue('roomTitle');
roomDescription.innerHTML = getQueryParameterValue('roomDescription');

answerString1.style.cursor = "pointer";
answerString2.style.cursor = "pointer";
answerString3.style.cursor = "pointer";

answerString1.onclick = () => {
    answerString1.style.backgroundColor = '#ffd200';
    if (getQueryParameterValue('answer1') == 'true') {
        answerString1.classList.add('border-success');
    } else {
        answerString1.classList.add('border-danger');
    }
}

answerString2.onclick = () => {
    answerString2.style.backgroundColor = '#ffd200';
    if (getQueryParameterValue('answer2') == 'true') {
        answerString2.classList.add('border-success');
    } else {
        answerString2.classList.add('border-danger');
    }
}

answerString3.onclick = () => {
    answerString3.style.backgroundColor = '#ffd200';
    if (getQueryParameterValue('answer2') == 'true') {
        answerString3.classList.add('border-success');
    } else {
        answerString3.classList.add('border-danger');
    }
}