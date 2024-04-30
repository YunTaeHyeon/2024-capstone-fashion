#!/usr/bin/env python
# coding: utf-8

# In[1]:


from IPython.display import display
import os
from PIL import Image

def resize_images_in_directory(directory, target_size=(640, 640)):
    resized_images = []
    for filename in os.listdir(directory):
        if filename.endswith(".jpg") or filename.endswith(".jpeg"):
            filepath = os.path.join(directory, filename)
            try:
                # Open the image
                img = Image.open(filepath)
                # Resize the image
                img_resized = img.resize(target_size, Image.ANTIALIAS)
                # Save the resized image
                img_resized.save(filepath)
                resized_images.append(img_resized)
                print(f"{filename} resized successfully.")
            except Exception as e:
                print(f"Error resizing {filename}: {e}")
    return resized_images

# Directory path
directory_path = "/Users/taehyeonyun/coding/2024-capstone-fashion/recommendation-system/init_recommend_images"
# Resize images
resized_images = resize_images_in_directory(directory_path)


# In[2]:


# Display resized images
for image in resized_images:
    display(image)


# In[ ]:




