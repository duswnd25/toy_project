var query = {
    tag: 'duswnd25',
    limit: 10
};

var app = angular.module("app", []);

app.controller("app-controller", function ($scope) {
    var data = [];
    $scope.list = data;
    steem.api.getDiscussionsByFeed(query, function (err, response) {
        response.forEach(function (item) {
            var temp = {
                'title': item.title,
                'author': item.author,
                'description': item.body,
                'url': 'https://steemit.com' + item.url,
                'avatarSrc': 'https://steemitimages.com/u/' + item.author + '/avatar'
            };
            data.push(temp);
        });

        var loadingContainer = document.querySelector('.loading-container');
        document.body.removeChild(loadingContainer);

        $scope.list = data;
        $scope.$apply();
    });
});