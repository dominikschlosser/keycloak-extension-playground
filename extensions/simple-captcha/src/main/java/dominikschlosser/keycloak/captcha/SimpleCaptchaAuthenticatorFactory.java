package dominikschlosser.keycloak.captcha;

import com.google.auto.service.AutoService;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Arrays;
import java.util.List;

@AutoService(AuthenticatorFactory.class)
public class SimpleCaptchaAuthenticatorFactory implements AuthenticatorFactory {
   public static final String PROVIDER_ID = "simpleCaptcha";

   private final Authenticator SINGLETON = new SimpleCaptchaAuthenticator();

   @Override
   public String getDisplayType() {
      return "Simple Captcha";
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
      return Arrays.asList(
            new ProviderConfigProperty(SimpleCaptchaAuthenticator.LABEL_CONFIG, "Label",
                  "Welches Label soll dem Nutzer angezeigt werden?",
                  ProviderConfigProperty.STRING_TYPE,
                  "Bitte lösen sie folgende Aufgabe: ")
      );
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
