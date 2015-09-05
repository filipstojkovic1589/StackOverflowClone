$(document).ready(function () {
var hanlder = function () {
$.ajax({
url: 'services/PaginationGrid?page=' + $(this).text(),
success: function (data) {
$('#table').empty();
$('#table').append(data);
$(".callservice").bind("click", hanlder);
}
});
};
$.ajax({
url: 'services/PaginationGrid?page=1',
success: function (data) {
$('#table').empty();
$('#table').append(data);
$(".callservice").bind("click", hanlder);
}
});
});