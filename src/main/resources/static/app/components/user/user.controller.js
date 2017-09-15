(function() {
	'use strict';

	angular
		.module('ebook-user')
		.controller('UserController', UserController);

	UserController.$inject = ['User', '$state', 'localStorageService', '$fancyModal'];
	function UserController(User, $state, localStorageService, $fancyModal) {
		
		var ucr = this;
		ucr.user = localStorageService.get('user');
		ucr.currentState = $state.current.name;
		ucr.edit = edit;
		ucr.closeModal = closeModal;
		ucr.savePass = savePass;
		
		ucr.nameAndSurname = ucr.user.firstname + ' ' + ucr.user.lastname;
		
		function edit() {
			$state.go('main.userEdit', {username: ucr.user.username, id: ucr.user.id});
		}
		
		function closeModal() {
			$fancyModal.close(); 
		}
		
		function savePass() {
			
			if(ucr.newPassword == ucr.rePass) {
				localStorageService.set('newPassword', ucr.newPassword);
			} else {
				ucr.notEqualPass = true;
				return;
			}
			$fancyModal.close(); 
		}
	}
	
})();
