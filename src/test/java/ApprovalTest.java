import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.writers.ApprovalTextWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.lambda.functions.Function2;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApprovalTest {
    @Test
    @DisplayName("Test using approvals.verify()")
    void approvedTest(){
        Approvals approvals = new Approvals();
        Person john = new Person("다운", "정");
        Person jane = new Person("하나", "강");

        approvals.verify(john.getFullName() + " "+jane.getFullName());
//        Approvals.verify(john.getFullName() + jane.getFullName());
    }

    @Test
    @DisplayName("Approval Test using temporary file approvals. : FileApprover.apprveTextFile()")
    public void testApproveTextFile()
    {
        File f1 = createFile("file1 text");
        File f2 = createFile("file2 text");
        assertFalse(FileApprover.approveTextFile(f2, f1), "files are different");
        f2 = createFile("file1 text");
        assertTrue(FileApprover.approveTextFile(f2, f1), "files are the same");
    }

    @Test
    @DisplayName("Approval Test using file approvals with non-exsiting file : FileApprover.apprveTextFile()")
    public void testApproveTextFileWithNonExsitantFile()
    {
        File f1 = createFile("1");
        File f2 = new File("no exist");
        assertTrue(f1.exists());
        assertFalse(f2.exists());
        assertFalse(FileApprover.approveTextFile(f2, f1));
    }

    @Test
    @DisplayName("Approval Test using file approvals with exsiting file : FileApprover.apprveTextFile()")
    public void testApproveTextFileWithExsitantFile() throws IOException {
        File f1 = createFile("1");
        File f2 = new File("src\\test\\java\\customApproval.txt");
        if(!f2.exists()){
            f2.createNewFile();
        }
        assertTrue(f1.exists());
        assertTrue(f2.exists());
        assertTrue(FileApprover.approveTextFile(f2, f1));
    }

    private File createFile(String string)
    {
        try
        {
            File f = File.createTempFile("avc", "t");
            FileUtils.writeFile(f, string);
            return f;
        }
        catch (IOException e)
        {
            throw ObjectUtils.throwAsError(e);
        }
    }

    @Test
    @DisplayName("Customize Approval Test : ApprovalTextWriter, ApprovalNamer")
    public void testCustomApprover()
    {
        ApprovalTextWriter writer = new ApprovalTextWriter("Random: ",new String("customApprovedTest"));
        ApprovalNamer namer = Approvals.createApprovalNamer();
        Function2<File, File, Boolean> approveEverything = (r, a) -> true;
        Approvals.verify(new FileApprover(writer, namer, approveEverything));
    }

}

class ParameterizedApprovalTest {

    enum MyEnum {
        FOO, BAR
    }

    @Test
    void testVerifyAllWithEnumSource() {
        Approvals.verifyAll("enum source", MyEnum.values());
    }

    @ParameterizedTest
    @EnumSource(MyEnum.class)
    void testWithEnumSource(MyEnum e) {
        try (NamedEnvironment en = NamerFactory.withParameters(e))
        {
            // your code goes here
            Object output = e;
            Approvals.verify(output);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"parameter1", "parameter2", "parameter3"})
    void sampleParameterizedTest(String parameter)
    {
        try (NamedEnvironment en = NamerFactory.withParameters(parameter))
        {
            // your code goes here
            Object output = parameter;
            Approvals.verify(output);
        }
    }
}