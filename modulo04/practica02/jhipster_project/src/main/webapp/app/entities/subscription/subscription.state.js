(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subscription', {
            parent: 'entity',
            url: '/subscription',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Subscriptions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subscription/subscriptions.html',
                    controller: 'SubscriptionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('subscription-detail', {
            parent: 'entity',
            url: '/subscription/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Subscription'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subscription/subscription-detail.html',
                    controller: 'SubscriptionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Subscription', function($stateParams, Subscription) {
                    return Subscription.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'subscription',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('subscription-detail.edit', {
            parent: 'subscription-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscription/subscription-dialog.html',
                    controller: 'SubscriptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subscription', function(Subscription) {
                            return Subscription.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subscription.new', {
            parent: 'subscription',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscription/subscription-dialog.html',
                    controller: 'SubscriptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('subscription', null, { reload: 'subscription' });
                }, function() {
                    $state.go('subscription');
                });
            }]
        })
        .state('subscription.edit', {
            parent: 'subscription',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscription/subscription-dialog.html',
                    controller: 'SubscriptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subscription', function(Subscription) {
                            return Subscription.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subscription', null, { reload: 'subscription' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subscription.delete', {
            parent: 'subscription',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscription/subscription-delete-dialog.html',
                    controller: 'SubscriptionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Subscription', function(Subscription) {
                            return Subscription.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subscription', null, { reload: 'subscription' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
