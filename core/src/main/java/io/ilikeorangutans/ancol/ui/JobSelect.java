package io.ilikeorangutans.ancol.ui;

import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.ui.colony.JobSelectListener;

import java.util.Collection;

/**
 *
 */
public interface JobSelect {
	void select(Collection<Job> jobs);
	
	void addJobSelectListener(JobSelectListener listener);
}
