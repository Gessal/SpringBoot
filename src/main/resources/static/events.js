
$(document).ready(function(){
    // Кнопки отображения модального окна
    $('.modal_btn').on('click', function() {
        showModalWindow($(this).attr('num'), $(this).attr('act'));
    });

    // Добавление пользователя
    $('#add_button').on('click', function() {
        $.post('http://localhost:8080/admin/add',
            $('#add_user_data').serialize()).done(function (data) {
                if (data === null || data === '') {
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

    // Удаление/Редактирование пользователя
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
            $.post('http://localhost:8080/admin/update',
                {id: $('#mu_id').val(), name: $('#mu_name').val(), surname: $('#mu_surname').val(), age: $('#mu_age').val(),
                    username: $('#mu_username').val(), password: $('#mu_password').val(), roles : $('#mu_roles').val()})
                .done(function (data) {
                if (data === null || data === '') {
                    alert('Fail while updating user.')
                } else {
                    let roles = '';
                    $.each(data.roles, function (index, value) {
                        roles += value.role.replace('ROLE_', ' ');
                    })
                    $('#id_' + data.id).text(data.id);
                    $('#name_' + data.id).text(data.name);
                    $('#surname_' + data.id).text(data.surname);
                    $('#age_' + data.id).text(data.age);
                    $('#username_' + data.id).text(data.username);
                    $('#roles_' + data.id).empty();
                    $('#roles_' + data.id).text(roles);
                    alert('User is updated.');
                }
            });
        }
    });
});