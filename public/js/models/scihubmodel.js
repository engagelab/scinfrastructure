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
	url : "/group/postit/"
});
window.PostitComment = Backbone.Model.extend({
	urlRoot:"/comment/postit/",
	defaults:{
        "content":"",
        "postitId":""
    }
});
window.PostitCommentCollection = Backbone.Collection.extend({
	model : PostitComment,
	url : "/comment/postit/"
});


/* Youtube video service section */
window.Video = Backbone.Model.extend({});
window.VideoCollection = Backbone.Collection.extend({
	model : Video,
	url : "/group/video/"
});
window.VideoComment = Backbone.Model.extend({
	urlRoot:"/comment/video/",
	defaults:{
        "content":"",
        "videoId":""
    }
});
window.VideoCommentCollection = Backbone.Collection.extend({
	model : VideoComment,
	url : "/comment/video/"
});


/* Picture service section */
window.Picture = Backbone.Model.extend({});
window.PictureCollection = Backbone.Collection.extend({
	model : Picture,
	url : "/group/image/"
});
window.PictureComment = Backbone.Model.extend({
	urlRoot:"/group/image/",
	defaults:{
        "content":"",
        "imageId":""
    }
});
window.PictureCommentCollection = Backbone.Collection.extend({
	model : PictureComment,
	url : "/comment/image/"
});