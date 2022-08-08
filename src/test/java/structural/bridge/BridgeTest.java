package structural.bridge;

import org.junit.jupiter.api.Test;
import structural.bridge.*;

public class BridgeTest {

    @Test
    public void 추상적인것과_구체적인것을_분리하여_연결한다() {
        Champion kdaAhri = new Ahri(new KDA());
        Champion kdaAkail = new Akali(new KDA());
        Champion poolPartyAhri = new Ahri(new PoolParty());
        Champion poolPartyAkali = new Akali(new PoolParty());

        kdaAhri.move();
        kdaAhri.q();

        poolPartyAhri.move();
        poolPartyAhri.q();

        kdaAkail.move();
        kdaAhri.w();

        poolPartyAkali.move();
        poolPartyAkali.w();
    }

}
