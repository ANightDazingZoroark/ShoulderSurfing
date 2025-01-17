package com.teamderpy.shouldersurfing.client;

import com.teamderpy.shouldersurfing.config.Config;
import com.teamderpy.shouldersurfing.config.Perspective;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ShoulderInstance
{
	private static final ShoulderInstance INSTANCE = new ShoulderInstance();
	private boolean doShoulderSurfing;
	private boolean doSwitchPerspective;
	private boolean isAiming;
	private boolean canUseMountAim = false;
	private double offsetX = Config.CLIENT.getOffsetX();
	private double offsetY = Config.CLIENT.getOffsetY();
	private double offsetZ = Config.CLIENT.getOffsetZ();
	private double lastOffsetX = Config.CLIENT.getOffsetX();
	private double lastOffsetY = Config.CLIENT.getOffsetY();
	private double lastOffsetZ = Config.CLIENT.getOffsetZ();
	private double targetOffsetX = Config.CLIENT.getOffsetX();
	private double targetOffsetY = Config.CLIENT.getOffsetY();
	private double targetOffsetZ = Config.CLIENT.getOffsetZ();
	private int thirdPersonView = Config.CLIENT.getDefaultPerspective().getPointOfView();
	
	private ShoulderInstance()
	{
		super();
	}
	
	public void tick()
	{
		if(this.thirdPersonView != Minecraft.getMinecraft().gameSettings.thirdPersonView)
		{
			this.doShoulderSurfing = Config.CLIENT.replaceDefaultPerspective() && Minecraft.getMinecraft().gameSettings.thirdPersonView == 1;
		}
		
		if(!Perspective.FIRST_PERSON.equals(Perspective.current()))
		{
			this.doSwitchPerspective = false;
		}
		
		this.isAiming = ShoulderHelper.isHoldingAdaptiveItem() || (ShoulderHelper.isRidingRangedAttackMob() && this.canUseMountAim);
		
		if(this.isAiming && Config.CLIENT.getCrosshairType().doSwitchPerspective() && this.doShoulderSurfing)
		{
			this.changePerspective(Perspective.FIRST_PERSON);
			this.doSwitchPerspective = true;
		}
		else if(!this.isAiming && Perspective.FIRST_PERSON.equals(Perspective.current()) && this.doSwitchPerspective)
		{
			this.changePerspective(Perspective.SHOULDER_SURFING);
		}
		
		this.lastOffsetX = this.offsetX;
		this.lastOffsetY = this.offsetY;
		this.lastOffsetZ = this.offsetZ;
		
		this.offsetX = this.lastOffsetX + (this.targetOffsetX - this.lastOffsetX) * Config.CLIENT.getCameraTransitionSpeedMultiplier();		
		this.offsetY = this.lastOffsetY + (this.targetOffsetY - this.lastOffsetY) * Config.CLIENT.getCameraTransitionSpeedMultiplier();
		this.offsetZ = this.lastOffsetZ + (this.targetOffsetZ - this.lastOffsetZ) * Config.CLIENT.getCameraTransitionSpeedMultiplier();
	}
	
	public void changePerspective(Perspective perspective)
	{
		Minecraft.getMinecraft().gameSettings.thirdPersonView = perspective.getPointOfView();
		this.thirdPersonView = perspective.getPointOfView();
		this.doShoulderSurfing = Perspective.SHOULDER_SURFING.equals(perspective);
	}
	
	public boolean doShoulderSurfing()
	{
		return this.doShoulderSurfing;
	}
	
	public void setShoulderSurfing(boolean doShoulderSurfing)
	{
		this.doShoulderSurfing = doShoulderSurfing;
	}
	
	public boolean isAiming() {
		return this.isAiming;
	}

	public void setCanUseMountAim(boolean value) {
		this.canUseMountAim = value;
	}

	public boolean canUseMountAim() {
		return this.canUseMountAim;
	}
	
	public double getOffsetX()
	{
		return this.offsetX;
	}
	
	public double getOffsetXOld()
	{
		return this.lastOffsetX;
	}
	
	public double getOffsetY()
	{
		return this.offsetY;
	}
	
	public double getOffsetYOld()
	{
		return this.lastOffsetY;
	}
	
	public double getOffsetZ()
	{
		return this.offsetZ;
	}
	
	public double getOffsetZOld()
	{
		return this.lastOffsetZ;
	}
	
	public void setTargetOffsetX(double targetOffsetX)
	{
		this.targetOffsetX = targetOffsetX;
	}
	
	public void setTargetOffsetY(double targetOffsetY)
	{
		this.targetOffsetY = targetOffsetY;
	}
	
	public void setTargetOffsetZ(double targetOffsetZ)
	{
		this.targetOffsetZ = targetOffsetZ;
	}
	
	public static ShoulderInstance getInstance()
	{
		return INSTANCE;
	}
}
