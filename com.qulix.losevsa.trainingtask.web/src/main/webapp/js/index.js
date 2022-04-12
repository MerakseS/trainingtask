const form = document.querySelector('form');
const buttons = document.querySelector('form input[type=submit], form button[type=submit]');
form.addEventListener('submit', function(){
    buttons.disabled = true;
});