(function() {
	'use strict';

	angular
		.module('ebook-user')
		.controller('UserEditController', UserEditController);

	UserEditController.$inject = ['$scope', 'User', '$state', 'localStorageService', '$fancyModal', 'selectedUser', 'categories'];
	function UserEditController($scope, User, $state, localStorageService, $fancyModal, selectedUser, categories) {
		
		var uec = this;
		uec.user = selectedUser;
		uec.logedUser = localStorageService.get('user');
		uec.currentState = $state.current.name;
		uec.cancel = cancel;
		uec.newPass = newPass;
		uec.done = done;
		uec.remove = remove;
		uec.categories = categories;
		//uec.pdfUpload = pdfUpload;
		
		uec.nameAndSurname = uec.user.firstname + ' ' + uec.user.lastname;
		
		function cancel() {
			if($state.current.name == 'main.usersEdit') {
				$state.go('main.adminUsers');
				return;
			} 
			$state.go('main.userPage', {username: uec.user.username});
		}
		
		function newPass() {
			//acr.newPassFlag = true;
			$fancyModal.open({ templateUrl: 'app/components/user/changePassModal.html', 
				   			   controller: 'UserController as ucr'});
		}
		
		function done() {
			uec.submitted = true;
			if(uec.form.$invalid) {
				return;
			}
			
			if(localStorageService.get('newPassword') !== null && localStorageService.get('newPassword') !== undefined && localStorageService.get('newPassword') != '') {
				if(localStorageService.get('newPassword') != uec.user.password) {
					uec.user.password = localStorageService.get('newPassword');
					localStorageService.remove('newPassword');
				}
			}
			if(uec.user.id === localStorageService.get('user').id){
				localStorageService.set('user', uec.user);
			}
			if(uec.user.image != null && uec.user.image != undefined && uec.user.image != '') {
				if(uec.user.image.indexOf('image') == -1) {
	            	uec.notAnImage = true;
	            	return;
	            }
			}
			
			User.save(uec.user, cancel);
			
		}
		
		function remove() {
			User.remove(uec.user.id, successRemove);
		}
		
		function successRemove() {
			alert('User with id: '+ uec.user.id + ' has been successfully removed.');
			if($state.current.name == 'main.usersEdit') {
				$state.go('main.adminUsers');
				return;
			} 
			$state.go('main.userPage', {username: uec.user.username});
		}
		
		/*
		function pdfUpload() {
			//var f = document.getElementById('file').files[0],
			var f = uec.filePdf;
		      r = new FileReader();
		  r.onloadend = function(e){
		    var data = e.target.result;
		    //send your binary data via $http or $resource or do anything else with it
		  }
		   r.readAsArrayBuffer(f);
		}
		*/
	}
	
})();
