package dominikschlosser.keycloak.rolefilter;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.models.utils.RoleUtils;
import org.keycloak.sessions.AuthenticationSessionModel;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

public class RoleFilterAuthenticator implements Authenticator {
   private static final String TEMPLATE = "role-filter.ftl";

   @Override
   public void authenticate(AuthenticationFlowContext context) {
      Response response = prepareRoleSelection(context, null);
      context.challenge(response);
   }

   @Override
   public void action(AuthenticationFlowContext context) {
      MultivaluedMap<String, String> formParameters = context.getHttpRequest().getDecodedFormParameters();
      List<String> selectedRoles = formParameters.get("selectedRoles");

      AuthenticationSessionModel authSession = context.getAuthenticationSession();
      authSession.setUserSessionNote("SELECTED_ROLES", selectedRoles.stream().collect(Collectors.joining(";")));
      context.success();
   }

   private static Response prepareRoleSelection(AuthenticationFlowContext context, FormMessage errorMessage) {
      UserModel user = context.getUser();
      List<RoleModel> allRoles = RoleUtils.expandCompositeRolesStream(user.getRoleMappingsStream()).collect(Collectors.toList());

      LoginFormsProvider loginProvider = context.form().setAttribute("roles", allRoles);

      if(errorMessage != null) {
         loginProvider.addError(errorMessage);
      }

      return loginProvider.createForm(TEMPLATE);
   }

   @Override
   public boolean requiresUser() {
      return true;
   }

   @Override
   public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
      return true;
   }

   @Override
   public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

   }

   @Override
   public void close() {

   }
}
