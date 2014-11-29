package test;

import java.io.IOException;
import java.io.PrintWriter;

import org.objectweb.asm.*;
import org.objectweb.asm.util.*;
import org.objectweb.asm.Opcodes;

public class ClassPrinter extends ClassVisitor implements Opcodes{
	public ClassPrinter() {
		super(ASM4);
		
	}
	public ClassPrinter(ClassVisitor cv) {
		super(ASM4, cv);
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
