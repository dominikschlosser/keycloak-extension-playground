package dominikschlosser.keycloak.rolefilter;

import com.google.auto.service.AutoService;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Collections;
import java.util.List;

@AutoService(AuthenticatorFactory.class)
public class RoleFilterAuthenticatorFactory implements AuthenticatorFactory {
   public static final String PROVIDER_ID = "roleFilter";

   private final Authenticator SINGLETON = new RoleFilterAuthenticator();

   @Override
   public String getDisplayType() {
      return "Role Filter";
   }

   @Override
   public String getReferenceCategory() {
      return null;
   }

   @Override
   public boolean isConfigurable() {
      return true;
   }

   @Override
   public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
      return REQUIREMENT_CHOICES;
   }

   @Override
   public boolean isUserSetupAllowed() {
      return false;
   }

   @Override
   public String getHelpText() {
      return "";
   }

   @Override
   public List<ProviderConfigProperty> getConfigProperties() {
      return Collections.emptyList();
   }

   @Override
   public Authenticator create(KeycloakSession keycloakSession) {
      return SINGLETON;
   }

   @Override
   public void init(Config.Scope scope) {

   }

   @Override
   public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

   }

   @Override
   public void close() {

   }

   @Override
   public String getId() {
      return PROVIDER_ID;
   }
}
