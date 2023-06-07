const roomTitle = document.querySelector('input[name="roomTitle"]');
const roomDescription = document.querySelector('input[name="roomDescription"]');

function getQueryParameterValue(key) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get(key);
}

const form = document.querySelector('#form');
const hiddenInput = document.createElement('input');
hiddenInput.type = 'hidden';
hiddenInput.name = 'roomId';
hiddenInput.value = getQueryParameterValue('roomId');
form.appendChild(hiddenInput);

function fillFormFields() {
    roomTitle.value = getQueryParameterValue('roomTitle');
    roomDescription.value = getQueryParameterValue('roomDescription');
}

fillFormFields();