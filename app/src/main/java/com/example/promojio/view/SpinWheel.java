package com.example.promojio.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.promojio.R;

import java.util.Random;

public class SpinWheel extends Fragment {

    private ImageView wheelImageView;
    private Random random = new Random();
    private long lastSpinDuration = 0;
    private int lastAngle = 0;

    // Empty default constructor
    public SpinWheel() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wheel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wheelImageView = view.findViewById(R.id.wheelImageView);
        Button spinButton = view.findViewById(R.id.spinButton);

        spinButton.setOnClickListener(v -> spinWheel());
    }

    private void spinWheel() {
        int angle = 360*12 + (random.nextInt(360) + 1); // Ensures at least 4 rotations, plus a random amount.

        Log.d("SpinWheel", "angle: " + angle);

        lastSpinDuration = 3000 + random.nextInt(2000); // Duration between 3 and 5 seconds

        ObjectAnimator animator = ObjectAnimator.ofFloat(wheelImageView, "rotation", lastAngle, angle + lastAngle);
        animator.setDuration(lastSpinDuration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // This is called when the animation ends
                lastAngle += angle;
                lastAngle = lastAngle % 360; // Keep the angle within 0-359 degrees
                Log.d("SpinWheel", "Final angle: " + lastAngle);
                showResultPopup();
            }
        });
        animator.start();
    }

    private void showResultPopup() {
        // The wheel has been spun and we have the lastAngle recorded

        // Determine the prize based on the normalized angle.
        String prize;
        if ((lastAngle >= 343) || (lastAngle < 19)) {
            prize = "You won a mystery gift!";
        } else if (lastAngle < 55) {
            prize = "You won a free promocode!";
        } else if (lastAngle < 91) {
            prize = "Please try again";
        } else if (lastAngle < 127) {
            prize = "You won 50 coins!";
        } else if (lastAngle < 163) {
            prize = "You won a mystery gift!";
        } else if (lastAngle < 199) {
            prize = "Please try again";
        } else if (lastAngle < 235) {
            prize = "You won a free promocode!";
        } else if (lastAngle < 271) {
            prize = "You won a mystery gift!";
        } else if (lastAngle < 307) {
            prize = "Please try again";
        } else { // normalizedAngle < 343
            prize = "You won 50 coins!";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (prize.equals("Please try again")) {
            builder.setTitle("Sorry :(");
        }
        else {
            builder.setTitle("Congratulations!");
        }
        builder.setMessage(prize);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }


}
