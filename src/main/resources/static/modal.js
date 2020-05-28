$(document).ready(function(){
    $('.btn').on('click', function() {
        let u_id = $('#id_' + $(this).attr('id')).text();
        let u_name = $('#name_' + $(this).attr('id')).text();
        let u_surname = $('#surname_' + $(this).attr('id')).text();
        let u_age = $('#age_' + $(this).attr('id')).text();
        let u_username = $('#username_' + $(this).attr('id')).text();
        let u_roles = $('#roles_' + $(this).attr('id')).find('r');
        $('#mu_id').val(u_id);
        $('#mu_name').val(u_name);
        $('#mu_surname').val(u_surname);
        $('#mu_age').val(u_age);
        $('#mu_username').val(u_username);
        <!--Запихать роли в модальное окно-->
    });
});