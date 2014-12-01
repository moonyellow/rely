package adapter;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

import scheduler.Scheduler;

public class RecordMethodAdaptor extends MethodVisitor {

	private String superOwner;
	private int sdPos;
	private int lastLoadPos;
	private int state = 0;
	private boolean match = false;
	private boolean meetNextInt = false;

	public RecordMethodAdaptor(MethodVisitor mv, String owner, int access,
			String name, String desc) {
		super(Opcodes.ASM4, mv);
		this.superOwner = owner;
	}

	
	

	
	@Override
	public void visitCode() {
		// TODO Auto-generated method stub
		//System.out.println("**********************************************************");
		//		System.out.println(superOwner);
		//		System.out.println("**********************************************************");
		mv.visitCode();
		
		//mv.visitVarInsn(Opcodes.ASTORE, sdPos);
		mv.visitLdcInsn("record");
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "recorder/RecordTable",
				"getRecordTable", "(Ljava/lang/String;)Lrecorder/RecordTable;");
		mv.visitFieldInsn(Opcodes.PUTSTATIC, superOwner, "Retable", "Lrecorder/RecordTable;");
		
		mv.visitLdcInsn("record");
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "recorder/RandomTable",
				"getRandomTable", "(Ljava/lang/String;)Lrecorder/RandomTable;");
		mv.visitFieldInsn(Opcodes.PUTSTATIC, superOwner, "Rantable", "Lrecorder/RandomTable;");
		
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals("java/util/Random")
				&& name.equals("nextInt") && desc.equals("(I)I")) {

			meetNextInt = true;
			
		} else {
			mv.visitMethodInsn(opcode, owner, name, desc);
		}

	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		lastLoadPos = var;
		if(opcode == Opcodes.ISTORE){
			if(meetNextInt)
				match = true;
		}
		mv.visitVarInsn(opcode, var);
		if(match){
			match = false;
			meetNextInt =false;
			mv.visitFieldInsn(Opcodes.GETSTATIC, superOwner, "Rantable", "Lrecorder/RandomTable;");
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Thread", "currentThread", "()Ljava/lang/Thread;");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Thread", "getName", "()Ljava/lang/String;");
			mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
			mv.visitInsn(Opcodes.DUP);
			mv.visitLdcInsn("__data_");
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "recorder/RandomTable", "record", "(Ljava/lang/String;Ljava/lang/String;)V");

		}
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		mv.visitMaxs(maxStack + 4, maxLocals+4);
	}

	
	
	
	
}

