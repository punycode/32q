package de.punyco.thirtytwosquare.domain;

import de.punyco.thirtytwosquare.domain.UserAccount;
import de.punyco.thirtytwosquare.domain.Squarelet;
import org.datanucleus.api.jpa.annotations.Extension;

privileged aspect GoogleAppEngineDatanucleus {

    declare @field: * UserAccount.userId:@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true");
    declare @field: * Squarelet.id:@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true");
}
