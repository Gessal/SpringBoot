let mu_id = $('#mu_id');
let mu_name = $('#mu_name');
let mu_surname = $('#mu_surname');
let mu_age = $('#mu_age');
let mu_username = $('#mu_username');
let password_block = $('#password_block');
let act_button = $('#act_button');
let head_label = $('#head_label');
let mu_roles = $('#mu_roles');

function showModalWindow(n) {
    mu_id.val($('#id_' + n).text());
    mu_name.val($('#name_' + n).text());
    mu_surname.val($('#surname_' + n).text());
    mu_age.val($('#age_' + n).text());
    mu_username.val($('#username_' + n).text());
    $('#u_role_user').prop('selected', false);
    $('#u_role_admin').prop('selected', false);
    let u_roles = $('#roles_' + n).find('r');
    u_roles.each(function () {
        if($(this).text() === 'ADMIN') {
            $('#u_role_admin').prop('selected', true);
        } else if($(this).text() === 'USER') {
            $('#u_role_user').prop('selected', true);
        }
    });
    if ($(this).attr('act') === 'upd') {
        password_block.show();
        head_label.text('Edit user');
        mu_name.prop('disabled', false);
        mu_surname.prop('disabled', false);
        mu_age.prop('disabled', false);
        mu_username.prop('disabled', false);
        mu_roles.prop('disabled', false);
        act_button.text('Edit');
        act_button.removeClass();
        act_button.addClass('btn-primary');
        act_button.addClass('btn');
        act_button.attr('act', 'edit')
    } else {
        password_block.hide();
        head_label.text('Delete user');
        mu_name.prop('disabled', true);
        mu_surname.prop('disabled', true);
        mu_age.prop('disabled', true);
        mu_username.prop('disabled', true);
        mu_roles.prop('disabled', true);
        act_button.text('Delete');
        act_button.removeClass();
        act_button.addClass('btn-danger');
        act_button.addClass('btn');
        act_button.attr('act', 'delete')
    }
}