/* Group service section */
window.Group = Backbone.Model.extend({});
window.GroupCollection = Backbone.Collection.extend({
	model : Group,
	url : "/group"
});


/* Postit service section */
window.Postit = Backbone.Model.extend({});
window.PostitCollection = Backbone.Collection.extend({
	model : Postit,
	url : "/group/postits"
});
window.PostitComment = Backbone.Model.extend({
	urlRoot:"/postit/comments",
	defaults:{
        "content":"",
        "postitId":""
    }
});
window.PostitCommentCollection = Backbone.Collection.extend({
	model : PostitComment,
	url : "/postit/comments"
});


/* Youtube video service section */
window.Video = Backbone.Model.extend({});
window.VideoCollection = Backbone.Collection.extend({
	model : Video,
	url : "/video/group"
});
window.VideoComment = Backbone.Model.extend({
	urlRoot:"/video/comments",
	defaults:{
        "content":"",
        "videoId":""
    }
});
window.VideoCommentCollection = Backbone.Collection.extend({
	model : VideoComment,
	url : "/video/comments"
});


/* Picture service section */
window.Picture = Backbone.Model.extend({});
window.PictureCollection = Backbone.Collection.extend({
	model : Picture,
	url : "/group/images"
});
window.PictureComment = Backbone.Model.extend({
	urlRoot:"/image/comments",
	defaults:{
        "content":"",
        "imageId":""
    }
});
window.PictureCommentCollection = Backbone.Collection.extend({
	model : PictureComment,
	url : "/image/comments"
});