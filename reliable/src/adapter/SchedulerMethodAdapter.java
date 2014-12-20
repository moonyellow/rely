package adapter;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

import scheduler.Scheduler;

public class SchedulerMethodAdapter extends MethodVisitor {

	private String superOwner;
	private int sdPos;
	private int lastLoadPos;
	private int state = 0;

	public SchedulerMethodAdapter(MethodVisitor mv, String owner, int access,
			String name, String desc) {
		super(Opcodes.ASM4, mv);
		this.superOwner = owner;
	}

	
	

	@Override
	public void visitCode() {
		mv.visitCode();
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "scheduler/Scheduler",
				"getScheduler", "()Lscheduler/Scheduler;");
		//mv.visitVarInsn(Opcodes.ASTORE, sdPos);
		mv.visitFieldInsn(Opcodes.PUTSTATIC, superOwner, "sd", "Lscheduler/Scheduler;");
		
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals("java/lang/Thread")
				&& name.equals("start") && desc.equals("()V")) {

			// the original call
			mv.visitMethodInsn(opcode, owner, name, desc);

			//mv.visitVarInsn(Opcodes.ALOAD, lastLoadPos);
			mv.visitVarInsn(Opcodes.ALOAD, lastLoadPos);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Thread",
					"suspend", "()V");
			
			mv.visitFieldInsn(Opcodes.GETSTATIC, superOwner, "sd", "Lscheduler/Scheduler;");
			mv.visitVarInsn(Opcodes.ALOAD, lastLoadPos);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "scheduler/Scheduler",
					"addThread", "(Ljava/lang/Thread;)V");
			
		} else {
			mv.visitMethodInsn(opcode, owner, name, desc);
		}

	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		lastLoadPos = var;
		mv.visitVarInsn(opcode, var);
		
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		mv.visitMaxs(maxStack + 4, maxLocals+4);
	}

	
	
	
	
}
