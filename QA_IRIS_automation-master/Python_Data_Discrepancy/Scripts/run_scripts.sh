pip3 install --requirement requirements.txt > installed_libraries.txt
echo "Check installed_libraries.txt for pandas and requests install information"

echo "Running Institution Profile Style Script:"
python3 Institution_profile_style.py "$1"

#Print a blank line:
echo 

echo "Running Fund Profile Style Script:"
python3 Fund_profile_style.py "$1"

#Print a blank line:
echo

echo "Running Ownership Comparison Script for Institution:"
python3 Ownership_Institution_holder.py "$1"

#Print a blank line:
echo

echo "Running Ownership Comparison Script for Funds:"
python3 Ownership_Fund_holder.py "$1"

echo "Running Institution Profile page AUM Check Script:
python3 Insitution_profile_aum.py "$1"
