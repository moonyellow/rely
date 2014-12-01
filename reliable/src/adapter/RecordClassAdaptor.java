package adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.LocalVariablesSorter;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.*;

public class RecordClassAdaptor extends ClassVisitor {

	private String owner;
	private boolean isInterface;

	public RecordClassAdaptor() {
		super(Opcodes.ASM4);

	}

	public RecordClassAdaptor(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		cv.visit(version, access, name, signature, superName, interfaces);
		owner = name;
		isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
		//System.out.println("**********************************************************");
		//System.out.println(name+"  "+signature+"   "+superName);
		//System.out.println("**********************************************************");
		if (!isInterface) {
			FieldVisitor fv = cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "Retable",
					"Lrecorder/RecordTable;", null, null);
			if (fv != null) {
				fv.visitEnd();
			}
			
			FieldVisitor fv2 = cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "Rantable",
					"Lrecorder/RandomTable;", null, null);
			if (fv2 != null) {
				fv2.visitEnd();
			}
		}
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);
		if (!isInterface && mv != null && name.equals("updateData") && desc.equals("()V")) {
			RecordMethodAdaptor rma = new RecordMethodAdaptor(mv, owner,
					access, name, desc);
			return rma;
		}
		return mv;
	}

	@Override
	public void visitEnd() {
		
		cv.visitEnd();
	}

	public static void main(String[] args) throws IOException {
		ClassReader cr = new ClassReader("test.MainTest");

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		TraceClassVisitor tcv = new TraceClassVisitor(cw, new PrintWriter(
				System.out));
		RecordClassAdaptor cv = new RecordClassAdaptor(tcv);

		// ClassVisitor cv = new SchedulerClassAdapter(cw);
		cr.accept(cv, 0);

		byte[] b = cw.toByteArray();
		FileOutputStream fos = new FileOutputStream(new File("BenchSharedObjectRecord.class"));
		fos.write(b);
		fos.flush();
		fos.close();
	}
}
