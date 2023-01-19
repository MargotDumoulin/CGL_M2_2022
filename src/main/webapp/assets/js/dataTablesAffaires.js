/* Formatting function for row details - modify as you need */
function format(d) {
    return (
        'Liste des parrains et de leurs commissions associées :' +
        '<ul>' +
            showParrains(d) +
        '</u>'
    );
}

function showParrains (d) {
    let current = d.apporteur.parrain;
    let str = '';
    while (current) {
        str += `<li>${current.nom} ${current.prenom} ` + (current.isDeleted ? '(Supprimé) ' : '') +  ': ' + getCommissionAmount(current.id, d.commissions) + ' €</li>';
        current = current.parrain;
    }
    return str;
}

function getCommissionAmount(id, listCom) {
    for (com of listCom) {
        if (com.id.apporteur.id === id) {
            return com.montant;
        }
    }
    return 0;
}

function addOptionalParameters() {
    const currUrl = window.location.href;
    const params = currUrl.split('?')[1];
    return params ? ('?' + params) : '';
}

$(document).ready(function () {
    var dt = $('#affaires-table').DataTable({
        processing: true,
        serverSide: true,
        searching: false,
        autoWidth: false,
        ajax: '/CGL_M2_2022_war/affaires-data' + addOptionalParameters(),
        columns: [
            {
                className: 'dt-control',
                orderable: false,
                data: null,
                defaultContent: '',
            },
            { data: 'id' },
            {
                data: 'date',
                render: function (data) {
                    return new Date(data).toLocaleDateString();
                }
            },
            {
                data: 'commissionGlobale',
                render: function ( data ) {
                    return data + ' €';
                },
                width: "150px" },
            {
                data: 'apporteur',
                render: function ( data ) {
                    return data.prenom + ' ' + data.nom;
                },
            },
            {
                data: 'commissions',
                render: function ( data, _type, row ) {
                    for (com of data) {
                        if (com.id.apporteur.id == row['apporteur']['id']) {
                            return com.montant + ' €';
                        }
                    }
                    return 0 + ' €';
                },
                width: "50px",
                orderable: false,
            },
            {
                data: null,
                render: function ( data ) {
                    return '<div><a class="btn btn-primary" style="margin-right: 10px" href="add_affaire?affId=' + data.id + '" role="button"><i class="bi bi-pencil"></i></a>' +
                           '<a class="btn btn-danger" href="delete_affaire?affId=' + data.id + '" role="button"><i class="bi bi-trash3"></i></a></div>';
                },
                width: "220px",
                orderable: false,
            },
        ],
    });

    // Array to track the ids of the details displayed rows
    var detailRows = [];

    $('#affaires-table tbody').on('click', 'tr td.dt-control', function () {
        var tr = $(this).closest('tr');
        var row = dt.row(tr);
        var idx = detailRows.indexOf(tr.attr('id'));

        if (row.child.isShown()) {
            tr.removeClass('details');
            row.child.hide();

            // Remove from the 'open' array
            detailRows.splice(idx, 1);
        } else {
            tr.addClass('details');
            row.child(format(row.data())).show();

            // Add to the 'open' array
            if (idx === -1) {
                detailRows.push(tr.attr('id'));
            }
        }
    });

    // On each draw, loop over the `detailRows` array and show any child rows
    dt.on('draw', function () {
        detailRows.forEach(function(id, i) {
            $('#' + id + ' td.details-control').trigger('click');
        });
    });
});


