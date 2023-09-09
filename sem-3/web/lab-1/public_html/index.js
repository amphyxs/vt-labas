
const coordFormValidations = {
    x: false,
    y: false,
};

const validateCoordsForm = (event) => { 
    const inputName = event.target.name;
    const value = event.target.value;

    const isCorrect = (
        isNumeric(value) &&
        value &&
        isInsideBoundaries(parseIntTruncated(value), inputName)
    );

    const test = parseIntTruncated(value);

    coordFormValidations[inputName] = isCorrect;

    const allValidated = Object.values(coordFormValidations).every(el => el);
    document.getElementById('coords-submit').disabled = !allValidated;
}

const isNumeric = (num) => {
    return !isNaN(num)
}

const parseIntTruncated = (numString) => {
    maxDigits = 10;
    const truncated = numString.substring(0, maxDigits);
    return Number.parseInt(truncated);
}

const isInsideBoundaries = (coord, coordName) => {
    switch (coordName) {
        case 'x':
        case 'y':
            return -3 < coord && coord < 3;
        default:
            return true;
    }
}

const setClientTimezone = (htmlInput) => {
    const timezoneString = window.Intl.DateTimeFormat().resolvedOptions().timeZone;
    htmlInput.value = timezoneString;
}

window.onload = () => {
    document.getElementById('coords-submit').disabled = true;
    document.getElementById('x-coord-input').addEventListener('input', validateCoordsForm);
    document.getElementById('y-coord-input').addEventListener('input', validateCoordsForm);
    setClientTimezone(document.getElementById('timezone-input'));
};
