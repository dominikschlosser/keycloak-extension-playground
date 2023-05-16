package dominikschlosser.keycloak.captcha;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class SimpleCaptchaAuthenticator implements Authenticator {
   public static final String LABEL_CONFIG = "captcha.labelConfig";
   private static final String TEMPLATE = "captcha.ftl";
   private static final String SOLUTION_NOTE = "correctCaptchaSolution";

   @Override
   public void authenticate(AuthenticationFlowContext context) {
      Response response = prepareChallenge(context, null);
      context.challenge(response);
   }

   @Override
   public void action(AuthenticationFlowContext context) {
      MultivaluedMap<String, String> formParameters = context.getHttpRequest()
            .getDecodedFormParameters();
      List<String> captchaSolution = formParameters.get("captchaSolution");

      String solution = context.getAuthenticationSession().getAuthNote(SOLUTION_NOTE);
      if(captchaSolution.isEmpty() || !captchaSolution.get(0).equals(solution)) {
         FormMessage errorMessage = new FormMessage("captchaSolution",
               "Lösung ist falsch");

         Response response = prepareChallenge(context, errorMessage);
         context.challenge(response);
      } else {
         context.success();
      }
   }

   private static Response prepareChallenge(AuthenticationFlowContext context,
                                            FormMessage errorMessage) {
      int firstAddend = (int)(Math.random() * 10) + 1;
      int secondAddend = (int)(Math.random() * 10) + 1;

      String challengeText = String.format("Was ist %d + %d", firstAddend, secondAddend);
      context.getAuthenticationSession().setAuthNote(SOLUTION_NOTE, String.valueOf(firstAddend + secondAddend));

      Map<String, String> config = context.getAuthenticatorConfig().getConfig();
      LoginFormsProvider loginProvider = context.form()
            .setAttribute("challengeText", challengeText)
            .setAttribute("label", config.get(LABEL_CONFIG));

      if (errorMessage != null) {
         loginProvider.addError(errorMessage);
      }

      return loginProvider.createForm(TEMPLATE);
   }
   @Override
   public boolean requiresUser() {
      return false;
   }

   @Override
   public boolean configuredFor(KeycloakSession keycloakSession,
                                RealmModel realmModel, UserModel userModel) {
      return false;
   }

   @Override
   public void setRequiredActions(KeycloakSession keycloakSession,
                                  RealmModel realmModel, UserModel userModel) {

   }

   @Override
   public void close() {

   }
}
