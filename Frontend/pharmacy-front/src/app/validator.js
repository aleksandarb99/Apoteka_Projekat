function textValidator(input, required, pattern, minLength, maxLength) {
    let valid = true;
    if (!input && required) {
        return false;
    }
    if (!!pattern && !!input) {
        const main = pattern.match(/\/(.+)\/.*/)[1]
        const options = pattern.match(/\/.+\/(.*)/)[1]
        const regex = new RegExp(main, options);
        valid = valid && regex.test(input);
    }

    if (!!minLength && !!input) {
        valid = valid && input.length >= minLength;
    }

    if (!!maxLength && !!input) {
        valid = valid && input.length <= maxLength;
    }

    return valid;
}

function emailValidator(input, required = true) {
    return textValidator(input, required, "/^(([^<>()[\\]\\.,;:\\s@\"]+(\\.[^<>()[\\]\\.,;:\\s@\"]+)*)|(\".+\"))@(([^<>()[\\]\\.,;:\\s@\"]+\\.)+[^<>()[\\]\\.,;:\\s@\"]{2,})$/i", null, null)
}

function passwordValidator(input, required = true) {
    return textValidator(input, required, null, 6, 255)
}

function streetValidator(input, required = true) {
    return textValidator(input, required, null, 1, 150);
}

function cityValidator(input, required = true) {
    return textValidator(input, required, null, 1, 100);
}

function countryValidator(input, required = true) {
    return textValidator(input, required, null, 1, 100);
}

function firstNameValidator(input, required = true) {
    return textValidator(input, required, null, 1, 30);
}

function lastNameValidator(input, required = true) {
    return textValidator(input, required, null, 1, 60);
}

function phoneNumberValidator(input, required = true) {
    return textValidator(input, required, "/^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$/", 1, 30);
}

function additionalNotesValidator(input, required = true) {
    return textValidator(input, required, null, 0, 250);
}

function medicineCodeValidator(input, required = true) {
    return textValidator(input, required, null, 1, 30);
}

function medicineContentValidator(input, required = true) {
    return textValidator(input, required, null, 1, 200);
}

function medicineNameValidator(input, required = true) {
    return textValidator(input, required, null, 1, 50);
}

function sideEffectValidator(input, required = true) {
    return textValidator(input, required, null, 1, 250);
}

const Validator = {
    'email': emailValidator,
    'password': passwordValidator,
    'firstName': firstNameValidator,
    'lastName': lastNameValidator,
    'telephone': phoneNumberValidator,
    'city': cityValidator,
    'street': streetValidator,
    'country': countryValidator,
    'additionalNotes': additionalNotesValidator,
    'medicineCode': medicineCodeValidator,
    'medicineContent': medicineContentValidator,
    'medicineName': medicineNameValidator,
    'sideEffects': sideEffectValidator
}

export default Validator;