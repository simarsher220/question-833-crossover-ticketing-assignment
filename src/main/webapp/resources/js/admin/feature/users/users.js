(function() {
    'use strict';

    angular.module('adminApplication').component('users', {
        controller: ['$window', 'UserService', '$uibModal', '$q', UsersCtrl],
        templateUrl: '../resources/js/admin/feature/users/users.html',
        bindings: {
            title: '@',
            type: '@'
        }
    }).filter('userEnabled', function() {
        return function(list, activeRequired) {
            return _.filter(list, function(e) { return e.enabled === activeRequired })
        }
    }).filter('orgSelected', function() {
        return function(list, orgId) {
            if(orgId == null || !angular.isDefined(orgId)) {
                return list;
            }
            return _.filter(list, function(e) { return _.any(e.memberOf, function(i) { return i.id === orgId })})
        }
    }).filter('roleDesc', function() {
        return function(role, roleList) {
            if(role == null || roleList == null) {
                return role;
            }
            return _.filter(roleList, {role:role})[0].description;
        }
    });



    function UsersCtrl($window, UserService, $uibModal, $q) {
        var ctrl = this;

        ctrl.loadUsers = loadUsers;
        ctrl.deleteUser = deleteUser;
        ctrl.resetPassword = resetPassword;
        ctrl.enable = enable;
        ctrl.downloadApiKeys = downloadAllApiKeys;
        ctrl.viewApiKey = viewApiKeyQR;
        ctrl.selectedOrganization = null;

        ctrl.$onInit = function() {
            ctrl.users = [];
            ctrl.organizations = [];
            UserService.getAllRoles().then(function(roles) {
                ctrl.roles = roles.data;
            });
            loadUsers();
        };

        var filterFunction = function(user) { return ctrl.type === 'user' ^ user.type === 'API_KEY'; };

        function loadUsers() {
            self.loading = true;
            UserService.getAllUsers().then(function(result) {
                var filteredUsers = result.data.filter(filterFunction);
                ctrl.users = _.sortByOrder(filteredUsers, ['enabled','username'], [false, true]);
                ctrl.organizations = _.chain(result.data)
                    .map('memberOf')
                    .flatten()
                    .uniq(false, 'id')
                    .value();
                ctrl.loading = false;
            });
        }


        function deleteUser(user) {
            if($window.confirm('The ' + ctrl.type + ' ' + user.username + ' will be deleted. Are you sure?')) {
                UserService.deleteUser(user).then(function() {
                    loadUsers();
                });
            }
        }

        function resetPassword(user) {
            if($window.confirm('The password for the user '+ user.username+' will be reset. Are you sure?')) {
                UserService.resetPassword(user).then(function(reset) {
                    UserService.showUserData(reset.data);
                })
            }
        }

        function enable(user, status) {
            UserService.enable(user, status).then(function() {
                loadUsers();
            });
        }

        function downloadAllApiKeys(orgId) {
            if(angular.isDefined(orgId)) {
                $window.open('/admin/api/api-keys/organization/'+orgId+'/all');
            }
        }

        function viewApiKeyQR(user) {
            var modal = $uibModal.open({
                size:'sm',
                templateUrl:'../resources/js/admin/feature/users/api-key-qr.html',
                backdrop: 'static',
                controllerAs: 'ctrl',
                controller: function() {
                    var ctrl = this;
                    ctrl.user = user;
                    ctrl.qrCodeData = function() {
                        return {
                            apiKey: user.username,
                            baseUrl: $window.location.origin
                        };
                    };
                    ctrl.close = function() {
                        modal.close();
                    }
                }
            });
        }
    }
})();