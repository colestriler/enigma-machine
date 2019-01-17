package enigma;

import org.junit.Test;



public class MainTest {

    String _config = ""
            +
            "A-Z\n"
            +
            " 5 3\n"
            +
            " I MQ      (AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)\n"
            +
            " II ME     (FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)\n"
            +
            " III MV    (ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)\n"
            +
            " IV MJ     (AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)\n"
            +
            " V MZ      (AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)\n"
            +
            " VI MZM    (AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK) \n"
            +
            " VII MZM   (ANOUPFRIMBZTLWKSVEGCJYDHXQ) \n"
            +
            " VIII MZM  (AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)\n"
            +
            " Beta N    (ALBEVFCYODJWUGNMQTZSKPR) (HIX)\n"
            +
            " Gamma N   (AFNIRLBSQWVXGUZDKMTPCOYJHE)\n"
            +
            " B R       (AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP)\n"
            +
            "           (RX) (SZ) (TV)\n"
            +
            " C R       (AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) (PW)\n"
            +
            "           (QZ) (SX) (UY)";
    String _input1 = ""
            +
            "* B BETA III IV I AXLE\n"
            +
            "FROM his shoulder Hiawatha\n"
            +
            "Took the camera of rosewood\n"
            +
            "Made of sliding folding rosewood\n"
            +
            "Neatly put it all together\n"
            +
            "In its case it lay compactly\n"
            +
            "Folded into nearly nothing\n"
            +
            "But he opened out the hinges\n"
            +
            "Pushed and pulled the joints \n"
            +
            "   and hinges\n"
            +
            "Till it looked all squares \n"
            +
            "   and oblongs\n"
            +
            "Like a complicated figure\n"
            +
            "In the Second Book of Euclid";
    String _output1 = ""
            +
            "HYIHL BKOML IUYDC MPPSF SZW\n"
            +
            "SQCNJ EXNUO JYRZE KTCNB DGU\n"
            +
            "FLIIE GEPGR SJUJT CALGX SNCTM KUF\n"
            +
            "WMFCK WIPRY SODJC VCFYQ LV\n"
            +
            "QLMBY UQRIR XEPOV EUHFI RIF\n"
            +
            "KCGVS FPBGP KDRFY RTVMW GFU\n"
            +
            "NMXEH FHVPQ IDOAC GUIWG TNM\n"
            +
            "KVCKC FDZIO PYEVX NTBXY AHAO\n"
            +
            "BMQOP GTZX\n"
            +
            "VXQXO LEDRW YCMMW AONVU KQ\n"
            +
            "OUFAS RHACK\n"
            +
            "KXOMZ TDALH UNVXK PXBHA VQ\n"
            +
            "XVXEP UNUXT XYNIF FMDYJ VKH";

    String _input2 = ""
            +
            "* B BETA III IV I AXLE\n"
            +
            "HYIHL BKOML IUYDC MPPSF SZW\n"
            +
            "SQCNJ EXNUO JYRZE KTCNB DGU\n"
            +
            "FLIIE GEPGR SJUJT CALGX SNCTM KUF\n"
            +
            "WMFCK WIPRY SODJC VCFYQ LV\n"
            +
            "QLMBY UQRIR XEPOV EUHFI RIF\n"
            +
            "KCGVS FPBGP KDRFY RTVMW GFU\n"
            +
            "NMXEH FHVPQ IDOAC GUIWG TNM\n"
            +
            "KVCKC FDZIO PYEVX NTBXY AHAO\n"
            +
            "BMQOP GTZX\n"
            +
            "VXQXO LEDRW YCMMW AONVU KQ\n"
            +
            "OUFAS RHACK \n"
            +
            "KXOMZ TDALH UNVXK PXBHA VQ\n"
            +
            "XVXEP UNUXT XYNIF FMDYJ VKH";

    String _output2 = ""
            +
            "FROMH ISSHO ULDER HIAWA THA\n"
            +
            "TOOKT HECAM ERAOF ROSEW OOD\n"
            +
            "MADEO FSLID INGFO LDING ROSEW OOD\n"
            +
            "NEATL YPUTI TALLT OGETH ER\n"
            +
            "INITS CASEI TLAYC OMPAC TLY\n"
            +
            "FOLDE DINTO NEARL YNOTH ING\n"
            +
            "BUTHE OPENE DOUTT HEHIN GES\n"
            +
            "PUSHE DANDP ULLED THEJO INTS\n"
            +
            "ANDHI NGES\n"
            +
            "TILLI TLOOK EDALL SQUAR ES\n"
            +
            "ANDOB LONGS\n"
            +
            "LIKEA COMPL ICATE DFIGU RE\n"
            +
            "INTHE SECON DBOOK OFEUC LID";

    private String[] main1Input = {_config, _input1, _output1};

    private Main main1 = new Main(main1Input);



    /* ***** TESTS ***** */

    @Test
    public void testProcess() {
        main1.process();
    }

    @Test
    public void testReadConfig() {

    }

    @Test
    public void testReadRotor() {

    }

    @Test
    public void testSetUp() {

    }

    @Test
    public void testPrintMessageLine() {


    }
}
