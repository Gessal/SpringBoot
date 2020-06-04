
$(document).ready(function(){
    // Кнопки отображения модального окна
    $('.modal_btn').on('click', function() {
        showModalWindow($(this).attr('num'), $(this).attr('act'));
    });

    // Добавление пользователя
    $('#add_button').on('click', function() {
        $.post('http://localhost:8080/admin/add',
            $('#add_user_data').serialize())
            .done(function (data) {
                    let roles = '';
                    $.each(data.roles, function (index, value) {
                        roles +='<span>' + value.role.replace('ROLE_', '') + '</span> ';
                    })
                    $('#users_table').append('<tr>' +
                        '<td id="id_' + data.id + '" name="id">' + data.id + '</td>' +
                        '<td id="name_' + data.id + '" name="name">' + data.name + '</td>' +
                        '<td id="surname_' + data.id + '" name="surname">' + data.surname + '</td>' +
                        '<td id="age_' + data.id + '" name="age">' + data.age + '</td>' +
                        '<td id="username_' + data.id + '" name="username">' + data.username + '</td>' +
                        '<td id="roles_' + data.id + '" name="roles">' + roles + '</td>' +
                        '<form>' +
                            '<td><button num="' + data.id + '" act="upd" type="button" class="btn btn-info modal_btn" data-toggle="modal" data-target="#delete_modal" data-btype="delete">Edit</button></td>' +
                            '<td><button num="' + data.id + '" act="del" type="button" class="btn btn-danger modal_btn" data-toggle="modal" data-target="#delete_modal" data-btype="delete">Delete</button></td>' +
                        '</form>' +
                    '</tr>');
                    alert('User is added.');
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 406) {
                    alert('User is already exist.');
                } else if (jqXHR.status === 500) {
                    alert('Fail while adding user.');
                }
            });
    });

    // Удаление/Редактирование пользователя
    $('#act_button').on('click', function() {
        if ($(this).attr('act') === 'delete') {
            $.post('http://localhost:8080/admin/delete',
                {id : $('#mu_id').val()})
                .done(function () {
                    $('#tr_' + $('#mu_id').val()).remove();
                    alert('User deleted.')
                })
                .fail(function () {
                    alert('Error while deleting user.');
                });
        } else if ($(this).attr('act') === 'edit') {
            $.post('http://localhost:8080/admin/update',
                {id: $('#mu_id').val(), name: $('#mu_name').val(), surname: $('#mu_surname').val(), age: $('#mu_age').val(),
                    username: $('#mu_username').val(), password: $('#mu_password').val(), roles : $('#mu_roles').val()})
                .done(function (data) {
                    let roles = '';
                    $.each(data.roles, function (index, value) {
                        roles += '<span>' + value.role.replace('ROLE_', '') + '</span> ';
                    })
                    $('#id_' + data.id).text(data.id);
                    $('#name_' + data.id).text(data.name);
                    $('#surname_' + data.id).text(data.surname);
                    $('#age_' + data.id).text(data.age);
                    $('#username_' + data.id).text(data.username);
                    $('#roles_' + data.id).empty();
                    $('#roles_' + data.id).append(roles);
                    alert('User is updated.');
                })
                .fail(function () {
                    alert('Error while updating user.');
                });
        }
    });
});