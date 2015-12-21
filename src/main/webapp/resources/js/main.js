function DeleteConfirm(name) {
	return confirm("您确定要删除“" + name + "”吗？");
}

function CloseWindow() {
	window.close();
}

function ReturnDeptList() {
	opener.location = 'list';
	CloseWindow();
}

function OpenEditWindow(id){
	var EditWindow = window.open('edit?id=' + id,'editWindow','height=150,width=800,toolbar=no,menubar=no,scrollbars=no,resizable=no,status=no');
	EditWindow.focus();
	
	return false;
}