package dominikschlosser.keycloak.rolefilter;

import com.google.auto.service.AutoService;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.ProtocolMapper;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;
import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.IDToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AutoService(ProtocolMapper.class)
public class FilteredClientRolesTokenMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper {
   public static final String PROVIDER_ID = "oidc-filtered-roles-mapper";

   private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

   static {
      OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
      OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, FilteredClientRolesTokenMapper.class);
   }

   @Override
   public String getDisplayCategory() {
      return TOKEN_MAPPER_CATEGORY;
   }

   @Override
   public String getDisplayType() {
      return "Filtered Roles";
   }

   @Override
   public String getHelpText() {
      return "Maps only filtered roles, requires role-filter-authenticator to be configured in your login-flow";
   }

   @Override
   public List<ProviderConfigProperty> getConfigProperties() {
      return configProperties;
   }

   @Override
   public String getId() {
      return PROVIDER_ID;
   }

   protected void setClaim(IDToken token, ProtocolMapperModel mappingModel, UserSessionModel userSession) {
      String rolesValue = userSession.getNote("SELECTED_ROLES");
      if (rolesValue != null) {
         List<String> selectedRoles = Arrays.stream(rolesValue.split(";")).collect(Collectors.toList());

         String protocolClaim = mappingModel.getConfig().get(OIDCAttributeMapperHelper.TOKEN_CLAIM_NAME);
         token.getOtherClaims().put(protocolClaim, selectedRoles);
      }
   }
}
