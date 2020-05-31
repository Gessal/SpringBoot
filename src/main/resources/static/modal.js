$(document).ready(function(){
    // Кнопки отображения модального окна
    $('.modal_btn').on('click', function() {
        let n = $(this).attr('num');
        let mu_name = $('#mu_name');
        let mu_surname = $('#mu_surname');
        let mu_age = $('#mu_age');
        let mu_username = $('#mu_username');
        let password_block = $('#password_block');
        let act_button = $('#act_button');
        let head_label = $('#head_label');
        let mu_roles = $('#mu_roles');

        $('#mu_id').val($('#id_' + n).text());
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
    });

    // Добавление пользователя
    $('#add_button').on('click', function() {
        $.post('http://localhost:8080/admin/add',
            $('#add_user_data').serialize()).done(function (data) {
                if (data == null) {
                    alert('Fail while adding user.')
                }else if (data.id === -1) {
                    alert('User is already exist.');
                } else {
                    let roles = '';
                    $.each(data.roles, function (index, value) {
                        roles += value.role.replace('ROLE_', ' ');
                    })
                    $('#users_table').append('<tr>' +
                        '<td>' + data.id + '</td>' +
                        '<td>' + data.name + '</td>' +
                        '<td>' + data.surname + '</td>' +
                        '<td>' + data.age + '</td>' +
                        '<td>' + data.username + '</td>' +
                        '<td>' + roles + '</td>' +
                        '<form>' +
                            '<td><button num="' + data.id + '" act="upd" type="button" class="btn btn-info" data-toggle="modal" data-target="#delete_modal" data-btype="delete">Edit</button></td>' +
                            '<td><button num="' + data.id + '" act="del" type="button" class="btn btn-danger" data-toggle="modal" data-target="#delete_modal" data-btype="delete">Delete</button></td>' +
                        '</form>' +
                    '</tr>');
                    alert('User is added.');
                }
        });
    });

    // Удаление пользователя
    $('#act_button').on('click', function() {
        if ($(this).attr('act') === 'delete') {
            $.post('http://localhost:8080/admin/delete',
                {id : $('#mu_id').val()}).done(function (data) {
                if (data === true) {
                    $('#tr_' + $('#mu_id').val()).remove();
                    alert('User deleted.')
                } else {
                    alert('Error while deleting user.');
                }
            });
        } else if ($(this).attr('act') === 'edit') {
            alert('UPDATE');
        }
    });
});