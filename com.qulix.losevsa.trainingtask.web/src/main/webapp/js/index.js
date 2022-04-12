const buttons = document.querySelector('form input[type=submit], form button[type=submit]');
buttons.addEventListener('click', function(){
    this.disabled = true;
    this.form.submit();
});