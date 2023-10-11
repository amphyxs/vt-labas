
const coordFormValidations = {
    x: false,
    y: false,
    r: false,
};

const validateCoordsForm = (event) => { 
    const inputName = event.target.name;
    const value = event.target.value;

    const isCorrect = (
        isNumeric(value) &&
        value &&
        isInsideBoundaries(parseIntTruncated(value), inputName)
    );

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
    return Number(truncated);
}

const isInsideBoundaries = (coord, coordName) => {
    switch (coordName) {
        case 'x':
            return -3 <= coord && coord <= 5;
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

    const timezoneString = window.Intl.DateTimeFormat().resolvedOptions().timeZone;
    window.location = `${window.location.protocol}//${window.location.host}${pathname}results.php?timezone=${timezoneString}`;
}

const calculateCoeff = () => {
    const rect = document.getElementById('coords-interactive').getBoundingClientRect();
    return rect.width / 2.5;
}

const updateCoords = (event) => {
    if (!coordFormValidations.r) {
        return;
    }

    const rect = document.getElementById('coords-interactive').getBoundingClientRect();
    let x = event.clientX - rect.left;
    let y = event.clientY - rect.top;
    const r = document.getElementById('r-coord-input').value;
    
    const centerX = ((rect.width - 1) / 2 + 1);
    const centerY = ((rect.height - 1) / 2 + 1);
    const coeff = calculateCoeff();

    x -= centerX;
    y = -y + centerY;
    
    x = (x / coeff) * r;
    y = (y / coeff) * r;

    document.getElementById('x-coord-input').value = x.toFixed(3);
    document.getElementById('y-coord-input').value = y.toFixed(3);

    validateCoordsForm({
        target: document.getElementById('x-coord-input')
    });
    validateCoordsForm({
        target: document.getElementById('y-coord-input')
    });
}

const submitCoordsData = (event) => {
    if (!coordFormValidations.r) {
        alert('Параметр R не выбран');
        return;
    }

    document.getElementById('coords-form').submit();
}

const placePoints = () => {
    const points = document.getElementsByClassName('results-point');
    for (const pointElement of points) {
        let x = pointElement.getAttribute('x');
        let y = pointElement.getAttribute('y');
        let r = pointElement.getAttribute('r');

        const coeff = calculateCoeff();
        x = x * coeff / r;
        y = y * coeff / r;

        pointElement.style.cssText = `bottom: calc(${y}px + 50%); left: calc(${x}px + 50%);`
    }
}

window.onload = () => {
    document.getElementById('coords-submit').disabled = true;
    document.getElementById('x-coord-input').addEventListener('input', validateCoordsForm);
    document.getElementById('y-coord-input').addEventListener('input', validateCoordsForm);
    document.getElementById('r-coord-input').addEventListener('change', validateCoordsForm);
    document.getElementById('coords-interactive').addEventListener('mousemove', updateCoords);
    document.getElementById('coords-interactive').addEventListener('click', submitCoordsData);
    setClientTimezone(document.getElementById('timezone-input'));

    placePoints();
};
