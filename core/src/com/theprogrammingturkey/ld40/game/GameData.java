package com.theprogrammingturkey.ld40.game;

public class GameData
{
	private int totalBallsAvailable = 50;

	private int leftSideRoundScore = 0;
	private int leftSideGameScore = 0;
	private int leftSideBallsSaved = 0;

	private int rightSideRoundScore = 0;
	private int rightSideGameScore = 0;
	private int rightSideBallsSaved = 0;

	public void initRound()
	{
		leftSideRoundScore = 0;
		leftSideBallsSaved = totalBallsAvailable / 2;

		rightSideRoundScore = 0;
		rightSideBallsSaved = totalBallsAvailable / 2;
	}

	public void endRound()
	{
		if (leftSideRoundScore < rightSideRoundScore)
		{
			leftSideGameScore++;
		}
		else
		{
			rightSideGameScore++;
		}
	}

	public void leftSideRecivePuck()
	{
		this.leftSideRoundScore++;
	}

	public void rightSideRecivePuck()
	{
		this.rightSideRoundScore++;
	}

	public boolean sideShoot(boolean leftSide)
	{
		if (leftSide)
		{
			if (leftSideBallsSaved > 0)
			{
				leftSideBallsSaved--;
				return true;
			}
			return false;
		}
		else
		{
			if (rightSideBallsSaved > 0)
			{
				rightSideBallsSaved--;
				return true;
			}
			return false;
		}
	}

	public void giveSideBall(boolean leftSide)
	{
		if (leftSide)
		{
			leftSideBallsSaved++;
		}
		else
		{
			rightSideBallsSaved++;
		}
	}

	public int getTotalBallsAvailable()
	{
		return totalBallsAvailable;
	}

	public int getLeftSideRoundScore()
	{
		return leftSideRoundScore;
	}

	public int getLeftSideGameScore()
	{
		return leftSideGameScore;
	}

	public int getLeftSideBallsSaved()
	{
		return leftSideBallsSaved;
	}

	public int getRightSideRoundScore()
	{
		return rightSideRoundScore;
	}

	public int getRightSideGameScore()
	{
		return rightSideGameScore;
	}

	public int getRightSideBallsSaved()
	{
		return rightSideBallsSaved;
	}

}
