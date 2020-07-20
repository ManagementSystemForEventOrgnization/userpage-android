package com.example.myapplication.util;

import android.graphics.Canvas;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.NotificationAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((NotificationAdapter.NotificationHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((NotificationAdapter.NotificationHolder) viewHolder).viewForeground;
        float ddX = dX/2;
        float ddY=dY/2;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, ddX, ddY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((NotificationAdapter.NotificationHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((NotificationAdapter.NotificationHolder) viewHolder).viewForeground;
        float ddX = dX/2;
        float ddY = dY/2;
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, ddX, ddY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
//    in notification
    //    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
//        if (viewHolder instanceof NotificationAdapter.NotificationHolder) {
//            // get the removed item name to display it in snack bar
//            String notificationId = notificationList.get(viewHolder.getAdapterPosition()).getId();
//            ((NotificationAdapter.NotificationHolder) viewHolder).delete_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mApiService.setDeleteNotification(notificationId).enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            if(response.isSuccessful())
//                            {
//                                mAdapter.removeItem(viewHolder.getAdapterPosition());
//                                Toast.makeText(mContext, "Deleted successfully!!", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                try {
//                                    JSONObject jsonError = new JSONObject(response.errorBody().string());
//                                    Log.e("debug", "onFailure: ERROR 600 > " + jsonError.getJSONObject("error").getString("message") );
//                                    Toast.makeText(mContext, jsonError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
//                                } catch (Exception e) {
//                                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                        }
//                    });
//                }
//            });
//            ((NotificationAdapter.NotificationHolder) viewHolder).markRead_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mApiService.setReadNotification(notificationId).enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            ((NotificationAdapter.NotificationHolder) viewHolder).viewForeground.setBackgroundColor(Color.parseColor("#111111"));
//                            Toast.makeText(mContext, "Marked successfully!!", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                        }
//                    });
//                }
//            });
//            // remove the item from recycler view
////            mAdapter.removeItem(viewHolder.getAdapterPosition());
//        }
//    }
}
