package adapter;

import java.io.IOException;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

public class SchedulerClassAdapter extends ClassVisitor {
	public SchedulerClassAdapter() {
		super(Opcodes.ASM4);
		
	}
	public SchedulerClassAdapter(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
		}
	public static void main(String[] args) throws IOException{
		ClassReader cr = new ClassReader("test.MainTest");
		
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
	    TraceClassVisitor tcv = new TraceClassVisitor(cw, new PrintWriter(System.out));
	    
	    cr.accept(tcv, 0);
	    //byte[] b = cw.toByteArray();
	    //for(int i=0;i<b.length;i++)
	    //	System.out.print((char)b[i]);
	}
}
