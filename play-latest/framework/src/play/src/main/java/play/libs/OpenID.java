package play.libs;

import java.util.Map;
import java.util.HashMap;

import scala.runtime.AbstractFunction1;
import scala.collection.JavaConversions;

import play.api.libs.concurrent.Promise;

import play.libs.F;
import play.mvc.Http;
import play.mvc.Http.Request;

/**
 * provides support for OpenID
 */
public class OpenID {

    /**
     * Retrieve the URL where the user should be redirected to start the OpenID authentication process
     */
    public static F.Promise<String> redirectURL(String openID, String callbackURL) {
        return redirectURL(openID, callbackURL, null, null);
    }

    /**
     * Retrieve the URL where the user should be redirected to start the OpenID authentication process
     */
    public static F.Promise<String> redirectURL(String openID, String callbackURL, Map<String, String> axRequired) {
        return redirectURL(openID, callbackURL, axRequired, null);
    }

    /**
     * Retrieve the URL where the user should be redirected to start the OpenID authentication process
     */
    public static F.Promise<String> redirectURL(String openID, String callbackURL, Map<String, String> axRequired, Map<String, String> axOptional) {
        if (axRequired == null) axRequired = new HashMap<String, String>();
        if (axOptional == null) axOptional = new HashMap<String, String>();
        return new F.Promise<String>(play.api.libs.openid.OpenID.redirectURL(openID,
                                                                             callbackURL,
                                                                             JavaConversions.mapAsScalaMap(axRequired).toSeq(),
                                                                             JavaConversions.mapAsScalaMap(axOptional).toSeq()));
    }

    /**
     * Check the identity of the user from the current request, that should be the callback from the OpenID server
     */
    public static F.Promise<UserInfo> verifiedId() {
        Request request = Http.Context.current().request();
        Promise<UserInfo> scalaPromise = play.api.libs.openid.OpenID.verifiedId(request.queryString()).map(
                new AbstractFunction1<play.api.libs.openid.UserInfo, UserInfo>() {
                    @Override
                    public UserInfo apply(play.api.libs.openid.UserInfo scalaUserInfo) {
                        return new UserInfo(scalaUserInfo.id(), JavaConversions.mapAsJavaMap(scalaUserInfo.attributes()));
                    }
                });
        return new F.Promise<UserInfo>(scalaPromise);
    }

    public static class UserInfo {
        public String id;
        public Map<String, String> attributes;
        public UserInfo(String id) {
            this.id = id;
            this.attributes = new HashMap<String, String>();
        }
        public UserInfo(String id, Map<String, String> attributes) {
            this.attributes = attributes;
        }
    }

}
