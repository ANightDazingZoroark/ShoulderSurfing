package com.teamderpy.shouldersurfing.asm.transformer;

import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

import com.teamderpy.shouldersurfing.asm.Mappings;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IMethodTransformer extends ITransformer
{
	InsnList getSearchList(Mappings mappings);
	
	void transform(MethodNode method, Mappings mappings, int offset);
	
	default boolean ignoreLabels()
	{
		return true;
	}
	
	default boolean ignoreLineNumber()
	{
		return true;
	}
}