
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

const openResults = () => {
    if (window.location.pathname.includes('index.html'))
        pathname = window.location.pathname.replace('index.html', '');
    else
        pathname = window.location.pathname;

    window.location = `${window.location.protocol}//${window.location.host}${pathname}results.php`;
}

window.onload = () => {
    document.getElementById('coords-submit').disabled = true;
    document.getElementById('x-coord-input').addEventListener('input', validateCoordsForm);
    document.getElementById('y-coord-input').addEventListener('input', validateCoordsForm);
    document.getElementById('coords-results').addEventListener('click', openResults);
    setClientTimezone(document.getElementById('timezone-input'));
};
