package adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.LocalVariablesSorter;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.*;

public class SchedulerClassAdapter extends ClassVisitor {

	private String owner;
	private boolean isInterface;

	public SchedulerClassAdapter() {
		super(Opcodes.ASM4);

	}

	public SchedulerClassAdapter(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		cv.visit(version, access, name, signature, superName, interfaces);
		owner = name;
		isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);
		if ( !isInterface && mv != null && !name.equals("<init>")) {
			SchedulerMethodAdapter sma = new SchedulerMethodAdapter(mv,owner,access, name, desc);
			sma.lvs = new LocalVariablesSorter(access, desc, sma);
			return sma;
		}
		return mv;
	}

	public static void main(String[] args) throws IOException {
		ClassReader cr = new ClassReader("test.MainTest");

		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		TraceClassVisitor tcv = new TraceClassVisitor(cw, new PrintWriter(System.out));
		SchedulerClassAdapter cv = new SchedulerClassAdapter(tcv);
		
		//ClassVisitor cv = new SchedulerClassAdapter(cw);
		cr.accept(cv, ClassReader.EXPAND_FRAMES);
		
		byte[] b = cw.toByteArray();
		FileOutputStream fos = new FileOutputStream(new File("MainTest.class"));
		fos.write(b);
		fos.flush();
		fos.close();
	}
}
