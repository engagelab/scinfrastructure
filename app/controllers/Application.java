package controllers;

import models.SGroup;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


public class Application extends Controller {
	

	public static Result index() {
			return ok(index.render());
	}
	
	
 // -- Authentication
    
    public static class Login {
        
        public String id;
        public String password;
        
        public String validate() {
            if(SGroup.authenticate(id, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
        
    }
    
    
    /**
     * Login page.
     */
    public static Result login() {
        return ok(
            login.render(form(Login.class))
        );
    }
	
    
    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("id", loginForm.get().id);
            return redirect(
                routes.Application.index()
            );
        }
    }
    
    
	  
//    @Inject
//    public static Morphia morphia;
//
//    public static class DistanceCalc {
//        @Constraints.Required
//        public String postcode;
//
//        @Constraints.Required
//        public int distance = 10;
//
//        public String validate() {
//            if (PostcodeUnit.find.field("postcode").equal(postcode).get() == null) {
//                return "Invalid postcode";
//            }
//
//            return null;
//        }
//    }
//
//    public static class Geocode {
//        @Constraints.Required
//        public String postcode;
//
//        public String validate() {
//            if (PostcodeUnit.find.field("postcode").equal(postcode).get() == null) {
//                return "Invalid postcode";
//            }
//
//            return null;
//        }
//    }
//
//    public static Result index() {
//        return ok(index.render(form(DistanceCalc.class), form(Geocode.class), new ArrayList<PostcodeUnit>()));
//    }
//
//    public static Result map() {
//        return ok(map.render());
//    }
//
//    public static Result ll() {
//        Form<Geocode> geocodeForm = form(Geocode.class).bindFromRequest();
//        if (geocodeForm.hasErrors()) {
//            return badRequest(index.render(form(DistanceCalc.class), geocodeForm, new ArrayList<PostcodeUnit>()));
//        } else {
//            Geocode geocode = geocodeForm.get();
//
//            PostcodeUnit unit = PostcodeUnit.find.field("postcode").equal(CharMatcher.WHITESPACE.removeFrom(geocode.postcode).toUpperCase()).get();
//            return ok(toJson(unit.location));
//        }
//    }
//
//    public static Result latLng(String postcode) {
//        if (Strings.isNullOrEmpty(postcode)) return badRequest("empty postcode");
//        postcode = CharMatcher.WHITESPACE.removeFrom(postcode).toUpperCase();
//        if (postcode.length() < 5 || postcode.length() > 7) return badRequest("illegal postcode format");
//
//        PostcodeUnit unit = PostcodeUnit.find.field("postcode").equal(postcode).get();
//        if (unit == null) {
//            return notFound();
//        } else {
//            return ok(toJson(unit.location));
//        }
//    }
//
//    public static Result en() {
//        Form<Geocode> geocodeForm = form(Geocode.class).bindFromRequest();
//        if (geocodeForm.hasErrors()) {
//            return badRequest(index.render(form(DistanceCalc.class), geocodeForm, new ArrayList<PostcodeUnit>()));
//        } else {
//            Geocode geocode = geocodeForm.get();
//
//            PostcodeUnit unit = PostcodeUnit.find.field("postcode").equal(CharMatcher.WHITESPACE.removeFrom(geocode.postcode).toUpperCase()).get();
//            return ok(toJson(unit.cartesianLocation));
//        }
//    }
//
//    public static Result eastingsNorthings(String postcode) {
//        if (Strings.isNullOrEmpty(postcode)) return badRequest("empty postcode");
//        postcode = CharMatcher.WHITESPACE.removeFrom(postcode).toUpperCase();
//        if (postcode.length() < 5 || postcode.length() > 7) return badRequest("illegal postcode format");
//
//        PostcodeUnit unit = PostcodeUnit.find.field("postcode").equal(postcode).get();
//        if (unit == null) {
//            return notFound();
//        } else {
//            return ok(toJson(unit.cartesianLocation));
//        }
//    }
//
//    public static Result near(String latitude, String longitude) {
//        return ok(toJson(findNearMiles(Double.parseDouble(latitude), Double.parseDouble(longitude), 15, 1000)));
//    }
//
//    public static Result calc() {
//        Form<DistanceCalc> distanceCalcForm = form(DistanceCalc.class).bindFromRequest();
//        if (distanceCalcForm.hasErrors()) {
//            return badRequest(index.render(distanceCalcForm, form(Geocode.class), new ArrayList<PostcodeUnit>()));
//        } else {
//            DistanceCalc calc = distanceCalcForm.get();
//
//            PostcodeUnit postcode = PostcodeUnit.find.field("postcode").equal(CharMatcher.WHITESPACE.removeFrom(calc.postcode).toUpperCase()).get();
//            List<PostcodeUnit> near = findNearMiles(postcode.location.latitude, postcode.location.longitude, calc.distance, 20);
//
//            flash("success", "Found " + near.size() + " post codes within " + calc.distance + " miles from " + postcode.postcode);
//
//            return ok(index.render(distanceCalcForm, form(Geocode.class), near));
//        }
//    }
//
//    protected static List<PostcodeUnit> findNearMiles(double latitude, double longitude, int miles, int limit) {
//        BasicDBObject geoNearCommand = new BasicDBObject();
//        geoNearCommand.put("geoNear", "pcu");
//        double coord[] = {latitude, longitude};
//        geoNearCommand.put("near", coord);
//        geoNearCommand.put("maxDistance", miles / 69.17);
//        geoNearCommand.put("num", limit);
//        geoNearCommand.put("spherical", true);
//
//        CommandResult geoNearResult = Model.datastore.getDB().command(geoNearCommand);
//        BasicDBList geoNearResults = (BasicDBList) geoNearResult.get("results");
//
//        return Lists.newArrayList(Iterables.transform(geoNearResults, new Function<Object, PostcodeUnit>() {
//            @Override
//            public PostcodeUnit apply(@Nullable Object input) {
//                BasicDBObject pcuObject = (BasicDBObject) ((BasicDBObject) input).get("obj");
//                return morphia.fromDBObject(PostcodeUnit.class, pcuObject);
//            }
//        }));
//    }
}