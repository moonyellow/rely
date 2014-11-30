package adapter;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

import scheduler.Scheduler;

public class SchedulerMethodAdapter extends MethodVisitor {

	private String owner;
	public LocalVariablesSorter lvs;
	private int sdPos;
	private int lastLoadPos;
	private int state;
	private static final int SEEN_NOTHING = 0;
	private static final int SEEN_ALOAD = 1;
	private static final int SEEN_INVOKEVIRTUAL = 2;

	public SchedulerMethodAdapter(MethodVisitor mv, String owner, int access,
			String name, String desc) {
		super(Opcodes.ASM4, mv);
		this.owner = owner;
	}

	/*
	@Override
	protected void onMethodEnter() {
		sdPos = newLocal(Type.getType(Scheduler.class));
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "scheduler/Scheduler",
				"getScheduler", "()Lscheduler/Scheduler;");
		mv.visitVarInsn(ASTORE, sdPos);
	}
	*/
	
	

	@Override
	public void visitCode() {
		// TODO Auto-generated method stub
		mv.visitCode();
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "scheduler/Scheduler",
				"getScheduler", "()Lscheduler/Scheduler;");
		sdPos = lvs.newLocal(Type.getType(Scheduler.class));
		mv.visitVarInsn(Opcodes.ASTORE, sdPos);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		if (state==SEEN_ALOAD && opcode == Opcodes.INVOKEVIRTUAL && owner.equals("java/lang/Thread")
				&& name.equals("start") && desc.equals("()V")) {
			mv.visitVarInsn(Opcodes.ALOAD, sdPos);
			mv.visitVarInsn(Opcodes.ALOAD, lastLoadPos);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "scheduler/Scheduler",
					"addThread", "(Ljava/lang/Thread;)V");

			// the original call
			mv.visitVarInsn(Opcodes.ALOAD, lastLoadPos);
			mv.visitMethodInsn(opcode, owner, name, desc);

			mv.visitVarInsn(Opcodes.ALOAD, lastLoadPos);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Thread",
					"suspend", "()V");
			state=SEEN_NOTHING;
		} else {
			visitAload();
			mv.visitMethodInsn(opcode, owner, name, desc);
		}

	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		lastLoadPos = var;
		state = SEEN_ALOAD;
		
		//mv.visitVarInsn(opcode, var);
	}

	private void visitAload() {
		if (state == SEEN_ALOAD) {
			mv.visitVarInsn(Opcodes.ALOAD, lastLoadPos);
		}
		state = SEEN_NOTHING;
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		mv.visitMaxs(maxStack + 4, maxLocals);
	}

	
	
	@Override
	public void visitFieldInsn(int arg0, String arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		visitAload();
		mv.visitFieldInsn(arg0, arg1, arg2, arg3);
	}

	@Override
	public void visitInsn(int arg0) {
		// TODO Auto-generated method stub
		visitAload();
		mv.visitInsn(arg0);
	}

	@Override
	public void visitIntInsn(int arg0, int arg1) {
		// TODO Auto-generated method stub
		visitAload();
		mv.visitIntInsn(arg0, arg1);
	}

	@Override
	public void visitInvokeDynamicInsn(String arg0, String arg1, Handle arg2,
			Object... arg3) {
		// TODO Auto-generated method stub
		visitAload();
		mv.visitInvokeDynamicInsn(arg0, arg1, arg2, arg3);
	}

	@Override
	public void visitJumpInsn(int arg0, Label arg1) {
		// TODO Auto-generated method stub
		visitAload();
		mv.visitJumpInsn(arg0, arg1);
	}

	@Override
	public void visitLdcInsn(Object arg0) {
		// TODO Auto-generated method stub
		visitAload();
		mv.visitLdcInsn(arg0);
	}

	@Override
	public void visitLookupSwitchInsn(Label arg0, int[] arg1, Label[] arg2) {
		// TODO Auto-generated method stub
		visitAload();
		mv.visitLookupSwitchInsn(arg0, arg1, arg2);
	}

	@Override
	public void visitMultiANewArrayInsn(String arg0, int arg1) {
		// TODO Auto-generated method stub
		visitAload();
		mv.visitMultiANewArrayInsn(arg0, arg1);
	}

	@Override
	public void visitTableSwitchInsn(int arg0, int arg1, Label arg2,
			Label... arg3) {
		// TODO Auto-generated method stub
		visitAload();
		mv.visitTableSwitchInsn(arg0, arg1, arg2, arg3);
	}

	@Override
	public void visitTypeInsn(int arg0, String arg1) {
		// TODO Auto-generated method stub
		visitAload();
		mv.visitTypeInsn(arg0, arg1);
	}
	
	
	
	
}
