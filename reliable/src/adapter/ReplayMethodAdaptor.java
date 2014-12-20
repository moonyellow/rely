package adapter;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

import scheduler.Scheduler;

import org.objectweb.asm.Opcodes;

public class ReplayMethodAdaptor extends MethodVisitor {

	private String superOwner;
	private int sdPos;
	private int lastLoadPos;
	private int state = 0;
	private boolean match = false;
	private boolean meetNextInt = false;

	private boolean done = false;

	public ReplayMethodAdaptor(MethodVisitor mv, String owner, int access,
			String name, String desc) {
		super(Opcodes.ASM4, mv);
		this.superOwner = owner;
	}

	@Override
	public void visitCode() {
		// TODO Auto-generated method stub
		// System.out.println("**********************************************************");
		// System.out.println(superOwner);
		// System.out.println("**********************************************************");
		mv.visitCode();

		// mv.visitVarInsn(Opcodes.ASTORE, sdPos);
		mv.visitLdcInsn("replay");
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "recorder/RecordTable",
				"getRecordTable", "(Ljava/lang/String;)Lrecorder/RecordTable;");
		mv.visitFieldInsn(Opcodes.PUTSTATIC, superOwner, "Retable",
				"Lrecorder/RecordTable;");

		mv.visitLdcInsn("replay");
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "recorder/RandomTable",
				"getRandomTable", "(Ljava/lang/String;)Lrecorder/RandomTable;");
		mv.visitFieldInsn(Opcodes.PUTSTATIC, superOwner, "Rantable",
				"Lrecorder/RandomTable;");

	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		if (done)
			return;

		if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals("java/util/Random")
				&& name.equals("nextInt") && desc.equals("(I)I")) {

			meetNextInt = true;
			mv.visitMethodInsn(opcode, owner, name, desc);

		} else {
			mv.visitMethodInsn(opcode, owner, name, desc);
		}

	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		if (done)
			return;
		lastLoadPos = var;
		if (opcode == Opcodes.ISTORE) {
			if (meetNextInt)
				match = true;
		}
		mv.visitVarInsn(opcode, var);
		if (match) {
			match = false;
			meetNextInt = false;
			done = true;
			mv.visitFieldInsn(Opcodes.GETSTATIC,
					"microbench/BenchSharedObject", "Rantable",
					"Lrecorder/RandomTable;");
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Thread",
					"currentThread", "()Ljava/lang/Thread;");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Thread",
					"getName", "()Ljava/lang/String;");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "recorder/RandomTable",
					"get", "(Ljava/lang/String;)Ljava/lang/String;");
			mv.visitVarInsn(Opcodes.ASTORE, 1);
			mv.visitFieldInsn(Opcodes.GETSTATIC,
					"microbench/BenchSharedObject", "Retable",
					"Lrecorder/RecordTable;");
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Thread",
					"currentThread", "()Ljava/lang/Thread;");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Thread",
					"getName", "()Ljava/lang/String;");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "recorder/RecordTable",
					"check", "(Ljava/lang/String;Ljava/lang/String;)V");
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitLdcInsn("__data_0");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String",
					"equals", "(Ljava/lang/Object;)Z");
			Label l0 = new Label();
			mv.visitJumpInsn(Opcodes.IFEQ, l0);
			mv.visitFieldInsn(Opcodes.GETSTATIC,
					"microbench/BenchSharedObject", "__data_0", "I");
			mv.visitInsn(Opcodes.ICONST_1);
			mv.visitInsn(Opcodes.IADD);
			mv.visitFieldInsn(Opcodes.PUTSTATIC,
					"microbench/BenchSharedObject", "__data_0", "I");
			Label l1 = new Label();
			mv.visitJumpInsn(Opcodes.GOTO, l1);
			mv.visitLabel(l0);
			mv.visitFrame(Opcodes.F_APPEND, 2, new Object[] {
					"java/lang/String", Opcodes.INTEGER }, 0, null);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitLdcInsn("__data_1");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String",
					"equals", "(Ljava/lang/Object;)Z");
			Label l2 = new Label();
			mv.visitJumpInsn(Opcodes.IFEQ, l2);
			mv.visitFieldInsn(Opcodes.GETSTATIC,
					"microbench/BenchSharedObject", "__data_1", "I");
			mv.visitInsn(Opcodes.ICONST_1);
			mv.visitInsn(Opcodes.IADD);
			mv.visitFieldInsn(Opcodes.PUTSTATIC,
					"microbench/BenchSharedObject", "__data_1", "I");
			mv.visitJumpInsn(Opcodes.GOTO, l1);
			mv.visitLabel(l2);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitLdcInsn("__data_2");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String",
					"equals", "(Ljava/lang/Object;)Z");
			Label l3 = new Label();
			mv.visitJumpInsn(Opcodes.IFEQ, l3);
			mv.visitFieldInsn(Opcodes.GETSTATIC,
					"microbench/BenchSharedObject", "__data_2", "I");
			mv.visitInsn(Opcodes.ICONST_1);
			mv.visitInsn(Opcodes.IADD);
			mv.visitFieldInsn(Opcodes.PUTSTATIC,
					"microbench/BenchSharedObject", "__data_2", "I");
			mv.visitJumpInsn(Opcodes.GOTO, l1);
			mv.visitLabel(l3);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitLdcInsn("__data_3");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String",
					"equals", "(Ljava/lang/Object;)Z");
			Label l4 = new Label();
			mv.visitJumpInsn(Opcodes.IFEQ, l4);
			mv.visitFieldInsn(Opcodes.GETSTATIC,
					"microbench/BenchSharedObject", "__data_3", "I");
			mv.visitInsn(Opcodes.ICONST_1);
			mv.visitInsn(Opcodes.IADD);
			mv.visitFieldInsn(Opcodes.PUTSTATIC,
					"microbench/BenchSharedObject", "__data_3", "I");
			mv.visitJumpInsn(Opcodes.GOTO, l1);
			mv.visitLabel(l4);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitLdcInsn("__data_4");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String",
					"equals", "(Ljava/lang/Object;)Z");
			mv.visitJumpInsn(Opcodes.IFEQ, l1);
			mv.visitFieldInsn(Opcodes.GETSTATIC,
					"microbench/BenchSharedObject", "__data_4", "I");
			mv.visitInsn(Opcodes.ICONST_1);
			mv.visitInsn(Opcodes.IADD);
			mv.visitFieldInsn(Opcodes.PUTSTATIC,
					"microbench/BenchSharedObject", "__data_4", "I");
			mv.visitLabel(l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitInsn(Opcodes.RETURN);

		}
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
		if (done)
			return;

		//shared variables are marked with __
		if (name.startsWith("__")
				&& (opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC)) {
			mv.visitFieldInsn(opcode, owner, name, desc);

			mv.visitFieldInsn(Opcodes.GETSTATIC, superOwner, "Retable",
					"Lrecorder/RecordTable;");
			mv.visitLdcInsn(name);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Thread",
					"currentThread", "()Ljava/lang/Thread;");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Thread",
					"getName", "()Ljava/lang/String;");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "recorder/RecordTable",
					"record", "(Ljava/lang/String;Ljava/lang/String;)V");

		} else {
			mv.visitFieldInsn(opcode, owner, name, desc);
		}
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		mv.visitMaxs(maxStack + 4, maxLocals + 4);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if (done)
			return null;
		return super.visitAnnotation(arg0, arg1);
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		// TODO Auto-generated method stub
		if (done)
			return null;
		return super.visitAnnotationDefault();
	}

	@Override
	public void visitAttribute(Attribute arg0) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitAttribute(arg0);
	}

	@Override
	public void visitFrame(int arg0, int arg1, Object[] arg2, int arg3,
			Object[] arg4) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitFrame(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void visitIincInsn(int arg0, int arg1) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitIincInsn(arg0, arg1);
	}

	@Override
	public void visitInsn(int arg0) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitInsn(arg0);
	}

	@Override
	public void visitIntInsn(int arg0, int arg1) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitIntInsn(arg0, arg1);
	}

	@Override
	public void visitInvokeDynamicInsn(String arg0, String arg1, Handle arg2,
			Object... arg3) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitInvokeDynamicInsn(arg0, arg1, arg2, arg3);
	}

	@Override
	public void visitJumpInsn(int arg0, Label arg1) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitJumpInsn(arg0, arg1);
	}

	@Override
	public void visitLabel(Label arg0) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitLabel(arg0);
	}

	@Override
	public void visitLdcInsn(Object arg0) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitLdcInsn(arg0);
	}

	@Override
	public void visitLineNumber(int arg0, Label arg1) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitLineNumber(arg0, arg1);
	}

	@Override
	public void visitLocalVariable(String arg0, String arg1, String arg2,
			Label arg3, Label arg4, int arg5) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitLocalVariable(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void visitLookupSwitchInsn(Label arg0, int[] arg1, Label[] arg2) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitLookupSwitchInsn(arg0, arg1, arg2);
	}

	@Override
	public void visitMultiANewArrayInsn(String arg0, int arg1) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitMultiANewArrayInsn(arg0, arg1);
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int arg0, String arg1,
			boolean arg2) {
		// TODO Auto-generated method stub
		if (done)
			return null;
		return super.visitParameterAnnotation(arg0, arg1, arg2);
	}

	@Override
	public void visitTableSwitchInsn(int arg0, int arg1, Label arg2,
			Label... arg3) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitTableSwitchInsn(arg0, arg1, arg2, arg3);
	}

	@Override
	public void visitTryCatchBlock(Label arg0, Label arg1, Label arg2,
			String arg3) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitTryCatchBlock(arg0, arg1, arg2, arg3);
	}

	@Override
	public void visitTypeInsn(int arg0, String arg1) {
		// TODO Auto-generated method stub
		if (done)
			return;
		super.visitTypeInsn(arg0, arg1);
	}

}
